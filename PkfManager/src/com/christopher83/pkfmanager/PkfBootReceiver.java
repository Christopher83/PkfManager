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

import com.christopher83.framework.properties.PropertyHashTable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Class for the receiver of the boot completed intent
 * @author Cristoforo Cataldo (Christopher83)
 */
public class PkfBootReceiver extends BroadcastReceiver {

	// Constant for the boot completed action
	private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

	/**
	 * Manages the received intent
	 */
	public void onReceive(final Context context, final Intent intent) {
		// If the action of the intent received is not a boot completed action, then exit
		if (!intent.getAction().equals(ACTION_BOOT))
			return;

		// If the Phantom Key Presses Filter module is supported
		if (PkfActivity.isPkfSupported(context)) {
			// Log the restoring process start
			Log.d(PkfBootReceiver.class.getName(), "Phantom Key Presses Filter - Restoring preferences...");

			// Restore the home key presses filtering parameters by setting the values of the related shared preferences
			PropertyHashTable homeKeyProperties = PkfCommon.getPkfHomeKeyProperties(context);
			homeKeyProperties.restore();

			// Restore the touch keys presses filtering parameters by setting the values of the related shared preferences
			PropertyHashTable touchKeysProperties = PkfCommon.getPkfTouchKeysProperties(context);
			touchKeysProperties.restore();

			// Log the restoring process end
			Log.d(PkfBootReceiver.class.getName(), "Phantom Key Presses Filter - Restoring preferences completed!");
		} else {
			// Log that the module is not currently supported
			Log.e(PkfBootReceiver.class.getName(), "Phantom Key Presses Filter - Module not found!");
		}
	}

}