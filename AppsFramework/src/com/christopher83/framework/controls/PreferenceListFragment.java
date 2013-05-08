/**
 *
 * Copyright (C) 2012, Fr4gg0r
 * Modified by Cristoforo Cataldo (Christopher83)
 * @see http://forum.xda-developers.com/showpost.php?p=19719977&postcount=1
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.christopher83.framework.R;

/**
 * Class for preference list
 * @author Fr4gg0r, modified by Cristoforo Cataldo (Christopher83)
 * @see http://forum.xda-developers.com/showpost.php?p=19719977&postcount=1
 */
@SuppressLint("HandlerLeak")
public class PreferenceListFragment extends ListFragment {

	private static final int FIRST_REQUEST_CODE = 100; // Starting request code given out to preference framework
	private static final int MSG_BIND_PREFERENCES = 0; // Code for preferences message bind handling

	protected int _preferencesID;                      // ID of the preferences resource file
	private PreferenceManager _preferenceManager;      // Preference manager
	private ListView _listView;                        // ListView where to add the layout of the preferences
	private OnPreferenceAttachedListener _listener;    // Event listener for preference attaching

	/**
	 * Message bind handler
	 */
	private Handler _handler = new Handler() {
		/**
		 * Handle the preferences binding
		 */
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == MSG_BIND_PREFERENCES)
				bindPreferences();
		}
	};

	/**
	 * Class constructor
	 */
	public PreferenceListFragment() {
	}

	/**
	 * Manages the preferences view creation
	 * @param inflater The layout inflater
	 * @param container The layout container
	 * @param bundle A mapping from String values to various Parcelable types
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		// Bind the preferences defined inside the resource file
		postBindPreferences();

		// Return the preferences list view
		return _listView;
	}

	/**
	 * Manages the preferences view destroy
	 */
	@Override
	public void onDestroyView() {
		// Invoke the base class method
		super.onDestroyView();

		// Remove the preferences list view from parent view
		ViewParent parent = _listView.getParent();
		if (parent != null)
			((ViewGroup)parent).removeView(_listView);
	}

	/**
	 * Manages the activity start and the preferences view layout creation
	 * @param bundle A mapping from String values to various Parcelable types
	 */
	@Override
	public void onCreate(Bundle bundle) {
		// Invoke the base class method
		super.onCreate(bundle);

		// If the bundle is not null, get the resource ID
		if (bundle != null)
			_preferencesID = bundle.getInt("xml");

		// Create the preferences manager
		_preferenceManager = onCreatePreferenceManager();

		// Create the preferences list view by inflating the preferences_list layout
		_listView = (ListView)LayoutInflater.from(getActivity()).inflate(R.layout.preferences_list, null);

		// Add the preferences from resource file and bind them
		addPreferencesFromResource(_preferencesID);
		postBindPreferences();

		// If not null, add the event listener for preference attaching
		if (_listener != null)
			_listener.onPreferenceAttached(getPreferenceScreen(), _preferencesID);
	}

	/**
	 * Manages the activity stop
	 */
	@Override
	public void onStop() {
		// Invoke the base class method
		super.onStop();

		// Invoke the activity stop management on preference manager
		try {
			Method m = PreferenceManager.class.getDeclaredMethod("dispatchActivityStop");
			m.setAccessible(true);
			m.invoke(_preferenceManager);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Manages the activity destroy
	 */
	@Override
	public void onDestroy() {
		// Invoke the base class method
		super.onDestroy();

		// Empty the preferences list view
		_listView = null;

		// Invoke the activity destroy management on preference manager
		try {
			Method m = PreferenceManager.class.getDeclaredMethod("dispatchActivityDestroy");
			m.setAccessible(true);
			m.invoke(_preferenceManager);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Manages the saving of current preferences state
	 * @param bundle A mapping from String values to various Parcelable types
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Save the current preferences state
		outState.putInt("xml", _preferencesID);
		super.onSaveInstanceState(outState);
	}

	/**
	 * Manages the activity exit result
	 * @param requestCode The request code of activity start
	 * @param resultCode The result code of activity exit
	 * @param data The intent to return result data to the caller
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Invoke the base class method
		super.onActivityResult(requestCode, resultCode, data);

		// Invoke the activity result management on preference manager
		try {
			Method m = PreferenceManager.class.getDeclaredMethod("dispatchActivityResult", int.class, int.class, Intent.class);
			m.setAccessible(true);
			m.invoke(_preferenceManager, requestCode, resultCode, data);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Posts a message to bind the preferences to the list view.
	 * Binding late is preferred as any custom preference types created in
	 * {@link #onCreate(Bundle)} are able to have their views recycled
	 */
	private void postBindPreferences() {
		if (_handler.hasMessages(MSG_BIND_PREFERENCES))
			return;
		_handler.obtainMessage(MSG_BIND_PREFERENCES).sendToTarget();
	}

	/**
	 * Binds the preferences to the preference screen
	 */
	private void bindPreferences() {
		// Get the preference screen
		final PreferenceScreen preferenceScreen = getPreferenceScreen();

		// If found, bind the preferences contained inside the preferences list view
		if (preferenceScreen != null)
			preferenceScreen.bind(_listView);
	}

	/**
	 * Creates the {@link PreferenceManager}
	 * @return The {@link PreferenceManager} used by this activity
	 */
	private PreferenceManager onCreatePreferenceManager() {
		try {
			// Preference manager initialization and creation
			Constructor<PreferenceManager> c = PreferenceManager.class.getDeclaredConstructor(Activity.class, int.class);
			c.setAccessible(true);
			PreferenceManager preferenceManager = c.newInstance(this.getActivity(), FIRST_REQUEST_CODE);

			// Return the created preference manager
			return preferenceManager;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the {@link PreferenceManager} used by this activity
	 * @return The {@link PreferenceManager}
	 */
	public PreferenceManager getPreferenceManager() {
		return _preferenceManager;
	}

	/**
	 * Sets the root of the preference hierarchy that this activity is showing
	 * @param preferenceScreen The root {@link PreferenceScreen} of the preference hierarchy
	 */
	public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
		try {
			Method m = PreferenceManager.class.getDeclaredMethod("setPreferences", PreferenceScreen.class);
			m.setAccessible(true);
			boolean result = (Boolean) m.invoke(_preferenceManager, preferenceScreen);
			if (result && preferenceScreen != null)
				postBindPreferences();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the root of the preference hierarchy that this activity is showing
	 * @return The {@link PreferenceScreen} that is the root of the preference hierarchy
	 */
	public PreferenceScreen getPreferenceScreen() {
		try {
			Method m = PreferenceManager.class.getDeclaredMethod("getPreferenceScreen");
			m.setAccessible(true);
			return (PreferenceScreen) m.invoke(_preferenceManager);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Adds preferences from activities that match the given {@link Intent}
	 * @param intent The {@link Intent} to query activities
	 */
	public void addPreferencesFromIntent(Intent intent) {
		throw new RuntimeException("too lazy to include this bs");
	}

	/**
	 * Inflates the given XML resource and adds the preference hierarchy to the current
	 * preference hierarchy
	 * @param preferencesResId The ID of the XML resource to inflate
	 */
	public void addPreferencesFromResource(int preferencesResId) {
		try {
			Method m = PreferenceManager.class.getDeclaredMethod("inflateFromResource", Context.class, int.class, PreferenceScreen.class);
			m.setAccessible(true);
			PreferenceScreen prefScreen = (PreferenceScreen) m.invoke(_preferenceManager, getActivity(), preferencesResId, getPreferenceScreen());
			setPreferenceScreen(prefScreen);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds a {@link Preference} based on its key
	 * @param key The key of the preference to retrieve
	 * @return The {@link Preference} with the key, or null
	 * @see PreferenceGroup#findPreference(CharSequence)
	 */
	public Preference findPreference(CharSequence key) {
		if (_preferenceManager != null)
			return _preferenceManager.findPreference(key);
		else
			return null;
	}

	/**
	 * Manages the preference attaching
	 * @param listener The event listener
	 */
	public void setOnPreferenceAttachedListener(OnPreferenceAttachedListener listener) {
		_listener = listener;
	}

	/**
	 * Interface for the preference attached listener
	 */
	public interface OnPreferenceAttachedListener {
		public void onPreferenceAttached(PreferenceScreen root, int xmlId);
	}

}
