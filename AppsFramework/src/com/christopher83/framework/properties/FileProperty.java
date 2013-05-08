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

import com.christopher83.framework.properties.interfaces.IFileProperty;
import com.christopher83.framework.utilities.IOUtilities;

/**
 * Base class for a property read/stored from a file
 * @author Cristoforo Cataldo (Christopher83)
 * @param <T> The type of the stored value
 */
public class FileProperty<T> implements IFileProperty<T> {

	private String _id;        // The ID of the property
	private String _path;      // The file path
	private boolean _readOnly; // Flag indicating if the property is readonly
	private T _defaultValue;   // The default value

	/**
	 * Class constructor
	 */
	public FileProperty() {
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 */
	public FileProperty(String id, String path) {
		this(id, path, false, null);
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 * @param readOnly Flag indicating if the property is readonly
	 */
	public FileProperty(String id, String path, boolean readOnly) {
		this(id, path, readOnly, null);
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 * @param defaultValue The default value
	 */
	public FileProperty(String id, String path, T defaultValue) {
		this(id, path, false, defaultValue);
	}

	/**
	 * Class constructor
	 * @param id The ID of the property
	 * @param path The file path
	 * @param readOnly Flag indicating if the property is readonly
	 * @param defaultValue The default value
	 */
	public FileProperty(String id, String path, boolean readOnly, T defaultValue) {
		_id = id;
		_path = path;
		_readOnly = readOnly;
		_defaultValue = defaultValue;
	}

	/**
	 * Gets the ID of the property
	 * @return The ID of the property
	 */
	@Override
	public String getID() {
		return _id;
	}

	/**
	 * Sets the ID of the property
	 * @param id The ID to set
	 */
	@Override
	public void setID(String id) {
		_id = id;
	}

	/**
	 * Gets the related file path of the property
	 * @return The file path
	 */
	@Override
	public String getPath() {
		return _path;
	}

	/**
	 * Sets the related file path of the property
	 * @param path The file path to set
	 */
	@Override
	public void setPath(String path) {
		_path = path;
	}

	/**
	 * Checks if the property is supported
	 * @return The flag indicating if the property is supported or not
	 */
	@Override
	public boolean isSupported() {
		// The property is supported if the related file exists
		return IOUtilities.fileExists(_path);
	}

	/**
	 * Checks if the property is readonly
	 * @return The flag indicating if the property is readonly or not
	 */
	@Override
	public boolean isReadOnly() {
		return _readOnly;
	}

	/**
	 * Sets the readonly status for the property
	 * @param readOnly The flag indicating if the property is readonly or not
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	/**
	 * Gets the default value of the property
	 * @return The default value
	 */
	@Override
	public T getDefaultValue() {
		return _defaultValue;
	}

	/**
	 * Sets the default value of the default value
	 * @param value The default value to set
	 */
	@Override
	public void setDefaultValue(T value) {
		_defaultValue = value;
	}

	/**
	 * Gets the current value of the property
	 * @return The current value
	 */
	@Override
	public T getValue() {
		// Must be implemented inside the extended class
		throw new UnsupportedOperationException();
	}

	/**
	 * Sets the current value of the property
	 * @param value The current value to set
	 */
	@Override
	public void setValue(Object value) {
		// Must be implemented inside the extended class
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the raw content of the file
	 * @return The current content of the file
	 */
	protected String readRawValue() {
		// If the property is suppported, then return the file content,
		// else return an empty string
		return (isSupported()) ? IOUtilities.fileRead(_path) : "";
	}

	/**
	 * Sets the raw content of the file
	 * @param value The value to store
	 */
	protected void storeRawValue(String value) {
		// If the property is suppported and is not readonly,
		// then store the passed value
		if (isSupported() && !isReadOnly())
			IOUtilities.fileWrite(_path, value);
	}

}
