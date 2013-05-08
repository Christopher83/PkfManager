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
package com.christopher83.pkfmanager;

import com.christopher83.pkfmanager.R;
import com.christopher83.framework.properties.BooleanFileProperty;
import com.christopher83.framework.properties.IntegerFileProperty;
import com.christopher83.framework.properties.PropertyHashTable;

import android.content.Context;

/**
 * Class for the common functions of the application
 * @author Cristoforo Cataldo (Christopher83)
 */
public class PkfCommon {

	/**
	 * Gets the properties related to the home key presses filtering
	 * @param context The application context
	 * @return The properties hash table related to the home key presses filtering
	 */
	public static PropertyHashTable getPkfHomeKeyProperties(final Context context) {
		// Initialize the properties hash table
		PropertyHashTable properties = new PropertyHashTable(context);

		// Add the file properties for the filtering parameters of the home key presses
		properties.put(new BooleanFileProperty(context.getString(R.string.id_homekey_filter_status), context.getString(R.string.path_homekey_filter_status)));
		properties.put(new IntegerFileProperty(context.getString(R.string.id_homekey_allowed_irqs), context.getString(R.string.path_homekey_allowed_irqs)));
		properties.put(new IntegerFileProperty(context.getString(R.string.id_homekey_report_wait), context.getString(R.string.path_homekey_report_wait)));
		properties.put(new IntegerFileProperty(context.getString(R.string.id_homekey_ignored_kp), context.getString(R.string.path_homekey_ignored_kp), true));

		// Return the properties hash table related to the home key presses filtering
		return properties;
	}

	/**
	 * Gets the properties related to the touch key presses filtering
	 * @param context The application context
	 * @return The properties hash table related to the touch key presses filtering
	 */
	public static PropertyHashTable getPkfTouchKeysProperties(final Context context) {
		// Initialize the properties hash table
		PropertyHashTable properties = new PropertyHashTable(context);

		// Add the file properties for the filtering parameters of the touch key presses
		properties.put(new BooleanFileProperty(context.getString(R.string.id_touchkeys_filter_status), context.getString(R.string.path_touchkeys_filter_status)));
		properties.put(new IntegerFileProperty(context.getString(R.string.id_touchkeys_interrupt_checks), context.getString(R.string.path_touchkeys_interrupt_checks)));
		properties.put(new IntegerFileProperty(context.getString(R.string.id_touchkeys_firsterr_wait), context.getString(R.string.path_touchkeys_firsterr_wait)));
		properties.put(new IntegerFileProperty(context.getString(R.string.id_touchkeys_lasterr_wait), context.getString(R.string.path_touchkeys_lasterr_wait)));
		properties.put(new IntegerFileProperty(context.getString(R.string.id_touchkeys_ignored_kp), context.getString(R.string.path_touchkeys_ignored_kp), true));

		// Return the properties hash table related to the touch key presses filtering
		return properties;
	}

}
