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
package com.christopher83.pkfmanager.tabs;

import android.os.Bundle;

import com.christopher83.framework.controls.TabPreferenceListFragment;
import com.christopher83.framework.properties.PropertyHashTable;
import com.christopher83.pkfmanager.PkfCommon;
import com.christopher83.pkfmanager.R;

/**
 * Class for the About tab fragment
 * @author Cristoforo Cataldo (Christopher83)
 */
public class TabAboutFragment extends TabPreferenceListFragment {

	/**
	 * Class constructor
	 */
	public TabAboutFragment() {
		// Invoke the base class constructor
		super();

		// Set the resource IDs for the title and the preferences showed by this tab
		_titleID = R.string.title_about;
		_preferencesID = R.xml.preferences_about;
	}

	/**
	 * Initialize the properties related to the preferences
	 * @return The properties related to the preferences
	 */
	@Override
	public PropertyHashTable initProperties() {
		return PkfCommon.getPkfAboutProperties(this.getActivity().getApplicationContext());
	}

	/**
	 * Manages the tab creation
	 */
	@Override
	public void onCreate(Bundle b) {
		// Invoke the base class method
		super.onCreate(b);

		// Set the ignored key presses preference to update its summary with the current value of the related property when clicked
		setPropertyPreferenceSummary(R.string.id_pkf_module_version);
	}
}