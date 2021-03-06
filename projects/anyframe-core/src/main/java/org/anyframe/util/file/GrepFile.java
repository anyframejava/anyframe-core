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
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to provide a subset of the grep functionality.
 * 
 * @author SoYon Lim
 * @author JongHoon Kim
 */
public abstract class GrepFile {
	private GrepFile() {

	}

	// Charset and decoder for ISO-8859-15
	private static Charset charset = Charset.forName("ISO-8859-15");

	private static CharsetDecoder decoder = charset.newDecoder();

	/**
	 * Check if the given file name contains the pattern
	 * 
	 * @param file
	 *            the file which is to be searched for the pattern
	 * @param pattern
	 *            the pattern that is being searched.
	 * @return true if the file contains the string, false otherwise.
	 */
	public static boolean containsPattern(File file, Pattern pattern) {
		// Pattern matcher
		Matcher pm = pattern.matcher(file.getName());
		if (pm.find())
			return true;

		return false;
	}

	/**
	 * Find all occurences of pattern in a file.
	 * 
	 * @param file
	 *            the file to search for occurences of pattern
	 * @param pattern
	 *            the pattern to search for
	 * @param group
	 *            which group in the pattern to return
	 * @return an <code>array</code> of occurences of pattern (i.e. the groupth
	 *         group of the match)
	 * @throws IOException
	 *             if the file could not be read.
	 */
	public static String[] findPattern(File file, Pattern pattern, int group)
			throws IOException {

		List<String> occurences = new ArrayList<String>();

		// Open the file and then get a channel from the stream
		FileInputStream fis = new FileInputStream(file);
		FileChannel fc = fis.getChannel();

		// Get the file's size and then map it into memory
		int sz = (int) fc.size();
		MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

		// Decode the file into a char buffer
		CharBuffer cb = decoder.decode(bb);

		// Perform the search
		Matcher pm = pattern.matcher(cb); // Pattern matcher

		while (pm.find()) {
			occurences.add(pm.group(group));
		}

		// Close the channel and the stream
		fc.close();
		fis.close();

		return occurences.toArray(new String[occurences.size()]);

	}

	/**
	 * Find all files below the given file which contain the given pattern.
	 * 
	 * @param file
	 *            the file where to start the search for the pattern.
	 * @param pattern
	 *            the pattern to search for.
	 * @return an array of files which contain the pattern
	 */
	private static List<File> findInternal(File file, Pattern pattern) {
		List<File> fileList = new ArrayList<File>();

		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				fileList.addAll(findInternal(new File(file.getAbsolutePath(),
						children[i]), pattern));
			}
		} else if (file.isFile() && containsPattern(file, pattern)) {
			fileList.add(file);
		}
		return fileList;
	}

	private static List<File> findInternalWithDirectory(File file, Pattern pattern) {
		List<File> fileList = new ArrayList<File>();

		if (file.isDirectory()) {
			fileList.add(file);
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				fileList.addAll(findInternalWithDirectory(new File(file
						.getAbsolutePath(), children[i]), pattern));
			}
		} else if (file.isFile() && containsPattern(file, pattern)) {
			fileList.add(file);
		}
		return fileList;
	}

	/**
	 * Find all files below the given file which contain the given search
	 * string.
	 * 
	 * @param file
	 *            the where to start the search
	 * @param searchString
	 *            the string to search for.
	 * @return an array of files which contain the search string.
	 */
	public static File[] find(File file, String searchString) {
		Pattern pattern = Pattern.compile(searchString);

		List<File> fileList = findInternal(file, pattern);
		return fileList.toArray(new File[fileList.size()]);
	}

	public static File[] find(File file, String searchString,
			boolean containsDirectory) {
		Pattern pattern = Pattern.compile(searchString);
		List<File> fileList = containsDirectory ? findInternalWithDirectory(
				file, pattern) : findInternal(file, pattern);
		return fileList.toArray(new File[fileList.size()]);
	}
}
