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
package org.anyframe.sample.logging;

import static org.junit.Assert.*;

import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test Case for testing standard Log4J configuration.
 * 
 * @author SoYon Lim
 * @author JongHoon Kim
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:./src/test/resources/spring/context-*.xml" })
public class StandardLog4jConfigurationTest{

	/**
	 * Test that a LogFactory gets created as expected.
	 */
	@Test
	public void testCreateFactory() {

		Log logger = LogFactory.getLog(getClass());

		assertNotNull("LogFactory exists", logger);
		assertEquals("LogFactory class",
				"org.apache.commons.logging.impl.Log4JLogger", logger
						.getClass().getName());
	}

	/**
	 * Tests if Prop File appender were added or not
	 */
	@Test
	public void testSampleConfiguration() {
		Enumeration enu = Logger.getRootLogger().getAllAppenders();

		if (enu.hasMoreElements()) {
			System.out.println();
			assertEquals("Log 4j Appender added ",
					"org.apache.log4j.RollingFileAppender", enu.nextElement()
							.getClass().getName());
		}

	}

}
