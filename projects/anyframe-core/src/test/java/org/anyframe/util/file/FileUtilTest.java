/*
 * Copyright 2002-2012 the original author or authors.
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
package org.anyframe.util.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * For testing functions what FileUtil supports, there are some test scenarios
 * in this TestCase.
 * 
 * @author SoYon Lim
 * @author JongHoon Kim
 */
public class FileUtilTest extends TestCase {

	public void testSearchFile() throws Exception {
		List<String> list = FileUtil.getFiles("./src/test/resources", "xml");
		assertTrue(list.size() > 0);
	}

	public void testSearchNonExistingFile() throws Exception {
		List<String> list = FileUtil.getFiles("./src/test/resources", "xml???");
		assertTrue(list.size() == 0);
	}

	public void testSearchDirectory() throws Exception {
		List<File> list = new ArrayList<File>();
		FileUtil.getDirs(list, "./src/test/resources");
		assertTrue(list.size() > 0);
	}
}
