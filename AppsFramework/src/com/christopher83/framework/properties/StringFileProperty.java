/**
 *
 * Copyright (C) 2013, Cristoforo Cataldo (Christopher83)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 */
package com.christopher83.framework.properties;

/**
 * Class for a string property read/stored from a file
 * @author Cristoforo Cataldo (Christopher83)
 */
public class StringFileProperty extends FileProperty<String> {

	/**
	 * Class constructor
	 */
	public StringFileProperty() {
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 */
	public StringFileProperty(String id, String path) {
		super(id, path, false, null);
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 * @param readOnly Flag indicating if the property is readonly
	 */
	public StringFileProperty(String id, String path, boolean readOnly) {
		super(id, path, readOnly, null);
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 * @param defaultValue The default value
	 */
	public StringFileProperty(String id, String path, String defaultValue) {
		super(id, path, false, defaultValue);
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 * @param readOnly Flag indicating if the property is readonly
	 * @param defaultValue The default value
	 */
	public StringFileProperty(String id, String path, boolean readOnly, String defaultValue) {
		super(id, path, readOnly, defaultValue);
	}

	/**
	 * Gets the string value of the property
	 * @return The string value of the property
	 */
	@Override
	public String getValue() {
		return readRawValue();
	}

	/**
	 * Sets the current value of the property
	 * @param value The current value to set
	 */
	@Override
	public void setValue(Object value) {
		storeRawValue((String)value);
	}

}
