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
 * Class for a boolean property read/stored from a file
 * @author Cristoforo Cataldo (Christopher83)
 */
public class BooleanFileProperty extends FileProperty<Boolean> {

	/**
	 * Class constructor
	 */
	public BooleanFileProperty() {
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 */
	public BooleanFileProperty(String id, String path) {
		super(id, path, false, null);
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 * @param readOnly Flag indicating if the property is readonly
	 */
	public BooleanFileProperty(String id, String path, boolean readOnly) {
		super(id, path, readOnly, null);
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 * @param defaultValue The default value
	 */
	public BooleanFileProperty(String id, String path, Boolean defaultValue) {
		super(id, path, false, defaultValue);
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 * @param readOnly Flag indicating if the property is readonly
	 * @param defaultValue The default value
	 */
	public BooleanFileProperty(String id, String path, boolean readOnly, Boolean defaultValue) {
		super(id, path, readOnly, defaultValue);
	}

	/**
	 * Gets the boolean value of the property
	 * @return The boolean value of the property
	 */
	@Override
	public Boolean getValue() {
		String value = super.readRawValue();
		if (value != null && !value.isEmpty())
			return value.equals("1") ? true : (value.equals("0") ? false : null);
		else
			return null;
	}

	/**
	 * Sets the current value of the property
	 * @param value The current value to set
	 */
	@Override
	public void setValue(Object value) {
		storeRawValue((value != null && value.equals(Boolean.TRUE)) ? "1" : "0");
	}

}
