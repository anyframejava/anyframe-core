/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.anyframe.datasource.sample.dsconfig;

import junit.framework.TestCase;

import org.anyframe.datasource.sample.some.JdbcSomeDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author SoYon Lim
 * @author JongHoon Kim
 */
public class SimpleConfigurationTest extends TestCase {

	ApplicationContext context;

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();

	}

	public void test_c3p0() throws Exception {
		context = new ClassPathXmlApplicationContext(
				"META-INF/spring/context-datasource-c3p0-unit.xml");

		JdbcSomeDAO sfBean = (JdbcSomeDAO) context.getBean("someDAO");

		assertEquals(sfBean.getJdbcTemplate().getDataSource().getClass()
				.getName(), "com.mchange.v2.c3p0.ComboPooledDataSource");

	}

	public void test_dbcp() throws Exception {
		context = new ClassPathXmlApplicationContext(
				"META-INF/spring/context-datasource-dbcp-unit.xml");

		JdbcSomeDAO sfBean = (JdbcSomeDAO) context.getBean("someDAO");

		assertEquals(sfBean.getJdbcTemplate().getDataSource().getClass()
				.getName(), "org.apache.commons.dbcp.BasicDataSource");

	}

	public void test_jdbc() throws Exception {
		context = new ClassPathXmlApplicationContext(
				"META-INF/spring/context-datasource-jdbc-unit.xml");

		JdbcSomeDAO sfBean = (JdbcSomeDAO) context.getBean("someDAO");

		assertEquals(sfBean.getJdbcTemplate().getDataSource().getClass()
				.getName(),
				"org.springframework.jdbc.datasource.DriverManagerDataSource");

	}

	public void test_jndi() throws Exception {
		context = new ClassPathXmlApplicationContext(
				"META-INF/spring/context-datasource-jndi-unit.xml");

		// JdbcSomeDAO sfBean = (JdbcSomeDAO) context.getBean("someDAO");
		//		
		// assertEquals(sfBean.getJdbcTemplate().getDataSource().getClass()
		// .getName(),
		// "org.springframework.jdbc.datasource.DriverManagerDataSource");

	}

}
