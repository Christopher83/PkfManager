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

import java.util.Hashtable;
import java.util.Map;

import com.christopher83.framework.properties.interfaces.IProperty;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Class for a hash table of properties
 * @author Cristoforo Cataldo (Christopher83)
 */
public class PropertyHashTable extends Hashtable<String, IProperty<?>> {

	// Serial version ID of the hash table type
	private static final long serialVersionUID = -5329554311716720160L;

	protected Context _context; // The application context

	/**
	 * Class constructor
	 * @param context The application context
	 */
	public PropertyHashTable(final Context context) {
		super();
		_context = context;
	}

	/**
	 * Gets the property by ID
	 * @param id The ID of the property to get
	 * @return The property, if found
	 */
	public IProperty<?> get(String id) {
		return super.get(id);
	}

	/**
	 * Gets the property by resource ID
	 * @param resID The resource ID of the property to get
	 * @return The property, if found
	 */
	public IProperty<?> get(int resID) {
		return super.get(_context.getString(resID));
	}

	/**
	 * Sets the value of the property
	 * @param id The ID of the property
	 * @param value The value to set
	 */
	public void set(String id, Object value) {
		this.get(id).setValue(value);
	}

	/**
	 * Sets the value of the property
	 * @param resID The resource ID of the property
	 * @param value The value to set
	 */
	public void set(int resID, Object value) {
		this.get(resID).setValue(value);
	}

	/**
	 * Checks if the hash table contains the specified ID
	 * @param id The ID of the property to find
	 * @return True if the hash table contains the specified ID
	 */
	public boolean containsKey(String id) {
		return super.containsKey(id);
	}

	/**
	 * Checks if the hash table contains the specified resource ID
	 * @param resID The resource ID of the property to find
	 * @return True if the hash table contains the specified resource ID
	 */
	public boolean containsKey(int resID) {
		return super.containsKey(_context.getString(resID));
	}

	/**
	 * Adds a new property item inside the hash table
	 * @param property The property to add
	 */
	public void put(IProperty<?> property) {
		// Add the new property using the ID of the property as key
		this.put(property.getID(), property);
	}

	/**
	 * Restores the values of the properties from the related shared preferences
	 */
	public void restore() {
		try {
			// Get the shared preferences values
			Map<String, ?> prefsItems = PreferenceManager.getDefaultSharedPreferences(_context).getAll();

			// For each property item
			for (IProperty<?> property:this.values()) {
				// If the property is not readonly
				if (!property.isReadOnly()) {
					// Get the preference value
					Object value = prefsItems.get(property.getID());

					// Set the property value with the preference value, if not null
					if (value != null)
						property.setValue(value);
				}
			}
		} catch (Exception ex) {
			Log.e(PropertyHashTable.class.getName(), "Error during preferences restore", ex);
		}
	}

	/**
	 * Checks for each shared preference if the value is changed and is different
	 * from the current value of the property, if so the preference will be updated
	 */
	public void checkAndUpdatePreferences() {
		try {
			// Get the shared preferences
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_context);

			// Get the values of the shared preferences
			Map<String, ?> prefsItems = prefs.getAll();

			// Get the editor used to update the changed preferences
			Editor prefsEditor = prefs.edit();

			// For each property contained inside the hash table
			for (IProperty<?> property:this.values()) {
				// If the property is not readonly
				if (!property.isReadOnly()) {
					// Get the current value of the property and of the shared preference
					Object propertyValue = property.getValue();
					Object preferenceValue = prefsItems.get(property.getID());

					// If the values are different, then update and store the preference value
					// with the current value of the property
					if (propertyValue != null && !propertyValue.equals(preferenceValue))
						updatePreference(prefsEditor, property.getID(), propertyValue);
				}
			}

			// Commit all the changes made to the shared preferences
			prefsEditor.commit();
		} catch (Exception ex) {
			Log.e(PropertyHashTable.class.getName(), "Error during preferences checking and update", ex);
		}
	}

	/**
	 * Updates the value of the specified shared preference
	 * @param prefsEditor The editor used to update the preferences
	 * @param id The ID of the preference
	 * @param value The value to set
	 */
	private void updatePreference(Editor prefsEditor, String id, Object value) {
		// Call the properly store method according to the type of the value
		if (value instanceof String)
			prefsEditor.putString(id, (String)value);
		else if (value instanceof Boolean)
			prefsEditor.putBoolean(id, (Boolean)value);
		else if (value instanceof Integer)
			prefsEditor.putInt(id, (Integer)value);
		else if (value instanceof String)
			prefsEditor.putLong(id, (Long)value);
		else
			throw new UnsupportedOperationException();
	}

}
