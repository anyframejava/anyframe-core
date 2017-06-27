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
package org.anyframe.logging;

import java.io.File;
import java.util.Enumeration;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

/**
 * For target what Logging Service supports, there are some test scenarios in
 * this TestCase.
 * 
 * @author SoYon Lim
 * @author JongHoon Kim
 */
public class RollingFileLoggerTest extends TestCase {

	/**
	 * [Flow #-1] Positive Case : try to log using RollingFileLogger
	 * 
	 * @throws Exception
	 *             fail to test
	 */
	public void testRollingFileLogging() {
		// 1. initialize
		File logFile = new File("./logs/rolling");
		File[] logFiles = logFile.listFiles();

		if (logFiles != null)
			for (int i = 0; i < logFiles.length; i++)
				logFiles[i].delete();

		// 2. find all appenders of RollingFileLoggger
		Enumeration appenders = Logger.getLogger(this.getClass())
				.getAllAppenders();

		// 3. check RollingFileLoggger configurations
		if (appenders.hasMoreElements()) {
			Appender appender = (Appender) appenders.nextElement();

			if ("org.apache.log4j.RollingFileAppender".equals(appender
					.getClass().getName())) {

				RollingFileAppender fileAppender = (RollingFileAppender) appender;
				String file = fileAppender.getFile();
				long fileSize = fileAppender.getMaximumFileSize();
				boolean append = fileAppender.getAppend();
				int backupIndex = fileAppender.getMaxBackupIndex();
				assertEquals("./logs/rolling/sample.log", file);
				assertEquals(1024, fileSize);
				assertTrue(append);
				assertEquals(2, backupIndex);
			}
		}

		// 4. try to log
		Log logger = LogFactory.getLog(this.getClass());
		assertTrue(logger.isInfoEnabled());
		for (int i = 0; i < 100; i++)
			logger.info("log - testRollingFileLoggingConf");

		// 5. check log files
		logFile = new File("./logs/rolling");
		logFiles = logFile.listFiles();
		assertEquals(3, logFiles.length);
	}
}
