/*
 * MIT License
 *
 * Copyright (c) 2020 Jim Liu.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jim.orm.mybatis.config;

import com.jim.orm.mybatis.config.datasource.ConstantsDatasource;
import com.jim.orm.mybatis.config.datasource.DynamicDataSourceContextHolder;
import com.jim.orm.mybatis.config.datasource.DynamicRoutingDatasource;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Setter
@Getter
@Configuration
@MapperScan(value = {"com.jim.orm.*.*.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
@Order(-1)
public class DatasourceConfig implements TransactionManagementConfigurer {

	private static final String MAPPER_LOCATION = "classpath*:mappers/**/*Mapper.xml";

	@Autowired
	private AppConfigBean appConfigBean;

	@Bean(name = "READ")
	DataSource readDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(appConfigBean.getJdbcUrl1());
		dataSource.setUsername(appConfigBean.getJdbcUsername1());
		dataSource.setPassword(appConfigBean.getJdbcPassword1());
		dataSource.setDriverClassName(appConfigBean.getJdbcDriverClassName1());
		return setupHikari(dataSource);
	}

	@Bean(name = "WRITE")
	@Primary
	DataSource writeDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(appConfigBean.getJdbcUrl2());
		dataSource.setUsername(appConfigBean.getJdbcUsername2());
		dataSource.setPassword(appConfigBean.getJdbcPassword2());
		dataSource.setDriverClassName(appConfigBean.getJdbcDriverClassName2());
		return setupHikari(dataSource);
	}

	private DataSource setupHikari(HikariDataSource dataSource) {
		dataSource.setConnectionTestQuery(appConfigBean.getJdbcConnectionTestQuery());
		dataSource.setConnectionTimeout(appConfigBean.getJdbcConnectionTimeout());
		dataSource.setIdleTimeout(appConfigBean.getJdbcIdleTimeout());
		dataSource.setMaxLifetime(appConfigBean.getJdbcMaxLifetime());
		dataSource.setMaximumPoolSize(appConfigBean.getJdbcMaxPoolSize());
		return dataSource;
	}

	@Bean(name = "dynamicDatasource")
	DataSource dynamicDatasource() {
		DynamicRoutingDatasource dynamicRoutingDatasource = new DynamicRoutingDatasource();
		Map<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceMap.put(ConstantsDatasource.READ.name(), readDataSource());
		dataSourceMap.put(ConstantsDatasource.WRITE.name(), writeDataSource());

		dynamicRoutingDatasource.setDefaultTargetDataSource(writeDataSource());
		dynamicRoutingDatasource.setTargetDataSources(dataSourceMap);

		DynamicDataSourceContextHolder.datasourceKeys.addAll(dataSourceMap.keySet());
		return dynamicRoutingDatasource;
	}

	@Autowired(required = false)
	private Interceptor[] interceptors;

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory(@Qualifier("dynamicDatasource") DataSource dataSource) throws IOException {

		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);

		// 添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			factoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATION));
			SqlSessionFactory factory = factoryBean.getObject();

			// 添加mybatis的自定义拦截器
			for (Interceptor interceptor : interceptors) {
				factory.getConfiguration().addInterceptor(interceptor);
			}

			factory.getConfiguration().setMapUnderscoreToCamelCase(true);
			return factoryBean.getObject();
		} catch (Exception e) {
			log.warn("getSqlSessionFactory failed, errorMessage:{}", e);
			throw new RuntimeException(e);
		}
	}

	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "annotationDrivenTransactionManager")
	@Override
	public DataSourceTransactionManager annotationDrivenTransactionManager() {
		try {
			DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
			transactionManager.setDataSource(dynamicDatasource());
			transactionManager.setDefaultTimeout(appConfigBean.getTransactionTimeout());
			return transactionManager;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
