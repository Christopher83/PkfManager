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
package com.christopher83.framework.properties.interfaces;

/**
 * Interface for a generic Property
 * @author Cristoforo Cataldo (Christopher83)
 * @param <T> The type of the stored value
 */
public interface IProperty<T> {

	/**
	 * Gets the ID of the property
	 * @return The ID of the property
	 */
	public abstract String getID();

	/**
	 * Sets the ID of the property
	 * @param id The ID to set
	 */
	public abstract void setID(String id);

	/**
	 * Checks if the property is supported
	 * @return The flag indicating if the property is supported or not
	 */
	public abstract boolean isSupported();

	/**
	 * Checks if the property is readonly
	 * @return The flag indicating if the property is readonly or not
	 */
	public abstract boolean isReadOnly();

	/**
	 * Sets the property readonly status
	 * @param readOnly The flag indicating if the property is readonly or not
	 */
	public abstract void setReadOnly(boolean readOnly);

	/**
	 * Gets the default value of the property
	 * @return The default value
	 */
	public abstract T getDefaultValue();

	/**
	 * Sets the default value of the property
	 * @param value The default value to set
	 */
	public abstract void setDefaultValue(T value);

	/**
	 * Gets the current value of the property
	 * @return The current value
	 */
	public abstract T getValue();

	/**
	 * Sets the current value of the property
	 * @param value The current value to set
	 */
	public abstract void setValue(Object value);

}