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
 * Interface for a FileProperty
 * @author Cristoforo Cataldo (Christopher83)
 * @param <T> The type of the stored value
 */
public interface IFileProperty<T> extends IProperty<T> {

	/**
	 * Gets the related file path of the property
	 * @return The file path
	 */
	public abstract String getPath();

	/**
	 * Sets the related file path of the property
	 * @param path The file path to set
	 */
	public abstract void setPath(String path);

}