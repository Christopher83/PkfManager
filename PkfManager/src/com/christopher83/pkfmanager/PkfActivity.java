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

import java.util.ArrayList;
import java.util.List;

import com.christopher83.framework.utilities.IOUtilities;
import com.christopher83.framework.controls.TabPreferenceListFragment;
import com.christopher83.pkfmanager.tabs.TabAboutFragment;
import com.christopher83.pkfmanager.tabs.TabHomeKeyFragment;
import com.christopher83.pkfmanager.tabs.TabTouchKeysFragment;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Class for main activity of this application
 * @author Cristoforo Cataldo (Christopher83)
 */
public class PkfActivity extends FragmentActivity implements ActionBar.TabListener {

	private SectionsPagerAdapter _sectionsPagerAdapter; // The adapter for the sections of the application
	private ViewPager _viewPager;                       // The ViewPager that contains the sections

	/**
	 * Checks if the Phantom Key Presses Filter module is supported
	 */
	public static boolean isPkfSupported(Context context) {
		// Check if the main sysfs folder exposed by the phantom_kp_filter module exists
		return IOUtilities.directoryExists(context.getString(R.string.path_pkf_module));
	}

	/**
	 * Shows an alert dialog for the kernel that doesn't support the Phantom Key Presses Filter module
	 */
	private void showPkfNotSupportedAlert() {
		// Create and set the alert dialog
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage("Sorry, but your kernel doesn't include the Phantom Key Presses Filter module.");
		alert.setCancelable(false);
		alert.setPositiveButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				PkfActivity.this.finish();
			}
		});

		// Show the dialog
		alert.show();
	}

	/**
	 * Manages the activity creation
	 */
	protected void onCreate(Bundle savedInstanceState) {
		// Invoke the base class method
		super.onCreate(savedInstanceState);

		// Set the content of the activity view
		setContentView(R.layout.activity_pkf);

		// If the kernel that doesn't support the Phantom Key Presses Filter module, then show an alert dialog
		if (!isPkfSupported(this))
			showPkfNotSupportedAlert();

		// Set up the action bar
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three primary sections of the app
		_sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter
		_viewPager = (ViewPager) findViewById(R.id.pager);
		_viewPager.setAdapter(_sectionsPagerAdapter);

		// When swiping between different sections, select the corresponding tab. We can also use
		// ActionBar.Tab#select() to do this if we have a reference to the Tab
		_viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar
		for (int i = 0; i < _sectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by the adapter.
			// Also specify this Activity object, which implements the TabListener interface,
			// as the callback (listener) for when this tab is selected
			actionBar.addTab(actionBar.newTab()
					 .setText(_sectionsPagerAdapter.getPageTitle(i))
					 .setTabListener(this));
		}
	}

	/**
	 * Manages the tab selected changed
	 */
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in the ViewPager
		_viewPager.setCurrentItem(tab.getPosition());
	}

	/**
	 * Manages the tab unselected event
	 */
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * Manages the tab reselected event
	 */
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * Class for the adapter for the showed sections of the application
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> _fragments; // The tab fragments of the application

		/**
		 * Class constructor
		 * @param fm The fragment manager
		 */
		public SectionsPagerAdapter(FragmentManager fm) {
			// Invoke the base class method
			super(fm);

			// Create the tab fragments showed by this application
			_fragments = new ArrayList<Fragment>();
			_fragments.add(new TabHomeKeyFragment());
			_fragments.add(new TabTouchKeysFragment());
			_fragments.add(new TabAboutFragment());
		}

		/**
		 * Gets the tab fragment at the specified position
		 * @param position The position of the fragment to get
		 * @return The tab fragment found at the specified position
		 */
		public Fragment getItem(int position) {
			return _fragments.get(position);
		}

		/**
		 * Gets the number of fragments
		 */
		public int getCount() {
			return _fragments.size();
		}

		/**
		 * Gets the title of the tab fragment at the specified position
		 * @param position The position of the tab fragment
		 */
		public CharSequence getPageTitle(int position) {
			return getString(((TabPreferenceListFragment)_fragments.get(position)).getTitleID());
		}
	}

}
