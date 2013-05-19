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
package com.christopher83.framework.controls;

import com.christopher83.framework.properties.PropertyHashTable;
import com.christopher83.framework.properties.interfaces.IProperty;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;

/**
 * Base class for a tab fragment of preferences
 * @author Cristoforo Cataldo (Christopher83)
 */
public class TabPreferenceListFragment
	extends PreferenceListFragment
	implements SharedPreferences.OnSharedPreferenceChangeListener {

	protected int _titleID;                  // The resource ID for the title of the tab
	protected PropertyHashTable _properties; // The properties related to the preferences

	/**
	 * Class constructor
	 */
	public TabPreferenceListFragment() {
		// Invoke the base class constructor
		super();
	}

	/**
	 * Gets the resource ID for the title of the tab
	 * @return The resource ID for the title of the tab
	 */
	public int getTitleID() {
		return _titleID;
	}

	/**
	 * Initialize the properties related to the preferences
	 * @return The properties related to the preferences
	 */
	public PropertyHashTable initProperties() {
		// If the properties are not null, this method need to be
		// overridden inside the extended class
		return null;
	}

	/**
	 * Manages the tab creation
	 */
	@Override
	public void onCreate(Bundle b) {
		// Initialize the properties related to the preferences
		_properties = initProperties();

		// If the properties hash table is not null, then check for each shared preference 
		// if the value is changed and is different from the current value of the file property,
		// if so the preference will be updated
		if (_properties != null)
			_properties.checkAndUpdatePreferences();

		// Invoke the base class creation method
		super.onCreate(b);

		// Register the event listener to manage the changes of shared preference values
		this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	/**
	 * Manages the changes of shared preference values
	 */
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		// If the changed preference is related to this tab, then store the new value
		if (_properties != null && _properties.containsKey(key))
			_properties.set(key, sharedPreferences.getAll().get(key));
	}

	/**
	 * Manages the pause event
	 */
	@Override
	public void onPause() {
		// Invoke the base class method
		super.onPause();

		// Unregister the event listener to manage the shared preference changes
		this.getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	/**
	 * Manages the resume event
	 */
	@Override
	public void onResume() {
		// Invoke the base class method
		super.onResume();

		// Register the event listener to manage the shared preference changes
		this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	/**
	 * Sets a preference to update its summary with the current value of the related property when clicked
	 * @param resourceID The resource ID of the property related to the preference to update
	 */
	protected void initUpdateablePropertyPreference(int resID) {
		initUpdateablePropertyPreference(_properties.get(resID));
	}

	/**
	 * Sets a preference to update its summary with the current value of the related property when clicked
	 * @param id The ID of the property related to the preference to update
	 */
	protected void initUpdateablePropertyPreference(String id) {
		initUpdateablePropertyPreference(_properties.get(id));
	}

	/**
	 * Sets a preference to update its summary with the current value of the related property when clicked
	 * @param property The property related to the preference to update
	 */
	protected void initUpdateablePropertyPreference(final IProperty<?> property) {
		// Get the preference related to the specified property
		Preference preference = (Preference)findPreference(property.getID());

		// Set the listener for the click event
		preference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			/**
			 * Manage the preference click by updating the summary content
			 */
			public boolean onPreferenceClick(Preference preference) {
				setPropertyPreferenceSummary(preference, property);
				return true;
			}
		});

		// Initialize the text of the summary with the current value of the property
		setPropertyPreferenceSummary(preference, property);
	}

	/**
	 * Sets the preference summary text with the property value
	 * @param resourceID The resource ID of the property related to the preference to set
	 */
	protected void setPropertyPreferenceSummary(int resID) {
		setPropertyPreferenceSummary(_properties.get(resID));
	}

	/**
	 * Sets the preference summary text with the property value
	 * @param id The ID of the property related to the preference to set
	 */
	protected void setPropertyPreferenceSummary(String id) {
		setPropertyPreferenceSummary(_properties.get(id));
	}

	/**
	 * Sets the preference summary text with the property value
	 * @param property The property related to the preference to set
	 */
	protected void setPropertyPreferenceSummary(final IProperty<?> property) {
		// Get the preference related to the specified property
		Preference preference = (Preference)findPreference(property.getID());

		// Initialize the text of the summary with the current value of the property
		setPropertyPreferenceSummary(preference, property);
	}

	/**
	 * Sets the preference summary text with the property value
	 * @param preference The preference to set the summary
	 * @param property The property related to the preference
	 */
	protected void setPropertyPreferenceSummary(Preference preference, final IProperty<?> property) {
		// Get the current value of the property
		Object value = property.getValue();

		// If not null, set the text of the summary with the string representation of the current value
		if (value != null) preference.setSummary(value.toString());
	}

}