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

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration(value = "appConfigBean")
@PropertySource("classpath:env/${spring.profiles.active}.properties")
@Getter
@ToString
public class AppConfigBean {
	//-------------------- jdbc datasource1 config read --------------------
	@Value("${jdbc.transaction.timeout}")
	private int transactionTimeout;

	@Value("${jdbc.datasource.url1}")
	private String jdbcUrl1;
	@Value("${jdbc.datasource.driverClassName1}")
	private String jdbcDriverClassName1;
	@Value("${jdbc.datasource.username1}")
	private String jdbcUsername1;
	@Value("${jdbc.datasource.password1}")
	private String jdbcPassword1;

	//-------------------- jdbc datasource2 config write --------------------
	@Value("${jdbc.datasource.url2}")
	private String jdbcUrl2;
	@Value("${jdbc.datasource.driverClassName2}")
	private String jdbcDriverClassName2;
	@Value("${jdbc.datasource.username2}")
	private String jdbcUsername2;
	@Value("${jdbc.datasource.password2}")
	private String jdbcPassword2;

	//-------------------- druid config write --------------------
	@Value("${jdbc.initialSize:10}")
	private Integer jdbcInitialSize;
	@Value("${jdbc.maxActive:50}")
	private Integer jdbcMaxActive;
	@Value("${jdbc.minIdle:10}")
	private Integer jdbcMinIdle;

	@Value("${jdbc.testConnection.query}")
	private String jdbcConnectionTestQuery;
	@Value("${jdbc.connectionTimeout}")
	private Long jdbcConnectionTimeout;
	@Value("${jdbc.clientIdleTimeout}")
	private Long jdbcIdleTimeout;
	@Value("${jdbc.maxLifetime}")
	private Long jdbcMaxLifetime;
	@Value("${jdbc.maxPoolSize}")
	private Integer jdbcMaxPoolSize;

	//---------------- redis config ----------------------
	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private Integer redisPort;
	@Value("${spring.redis.password}")
	private String redisPassword;
	@Value("${spring.redis.database}")
	private Integer redisDatabase;
	@Value("${spring.redis.maxIdle}")
	private Integer redisMaxIdle;
	@Value("${spring.redis.maxTotal}")
	private Integer redisMaxTotal;
	@Value("${spring.redis.timeout}")
	private Long redisTimeout;
}