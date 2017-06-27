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
package org.anyframe.spring.propertyeditor;

import java.beans.PropertyEditorSupport;

/**
 * CustomIntegerEditor
 * 
 * @author Jeryeon Kim
 */
public class CustomIntegerEditor extends PropertyEditorSupport {
	public CustomIntegerEditor() {
	}

	public String getAsText() {
		Integer d = (Integer) getValue();
		return d.toString();
	}

	public void setAsText(String str) {
		if (str==null || "".equals(str))
			setValue(0);
		else
			setValue(Integer.parseInt(str));
	}
}