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

package com.jim.orm.mybatis.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DynamicDatasourceAspect {
	@Before("@annotation(targetDatasource)")
	public void switchDatasource (JoinPoint point, TargetDatasource targetDatasource) {
		if (!DynamicDataSourceContextHolder.containDatasourceKey(targetDatasource.value())) {
			log.info("Datasource {} do not existed. use default datasource {} ", targetDatasource.value(), point.getSignature());
		} else {
			DynamicDataSourceContextHolder.setDatasourceKey(targetDatasource.value());
			log.info("Datasource switch to {} in method {}" , DynamicDataSourceContextHolder.getDatasourceKey(), point.getSignature());
		}
	}

	@After("@annotation(targetDatasource)")
	public void restoreDatasource(JoinPoint point, TargetDatasource targetDatasource) {
		DynamicDataSourceContextHolder.clearDatasourceKey();
		log.info("Datasource restore to {} in method {}" , DynamicDataSourceContextHolder.getDatasourceKey(), point.getSignature());
	}
}
