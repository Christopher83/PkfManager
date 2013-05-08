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
package com.christopher83.framework.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.util.Log;

/**
 * Class for I/O operations on filesystem
 * @author Cristoforo Cataldo (Christopher83)
 */
public class IOUtilities {

	/**
	 * Checks if the specified file path exists
	 * @param path The file path to check
	 * @return True if the path exists
	 */
	public static boolean fileExists(String path) {
		return new File(path).exists();
	}

	/**
	 * Checks if the specified path exists and is a directory
	 * @param path The directory path to check
	 * @return True if the path exists and is a directory
	 */
	public static boolean directoryExists(String path) {
		File directory = new File(path);
		return directory.exists() && directory.isDirectory();
	}

	/**
	 * Reads from a file
	 * @param path The file path
	 * @return The content of the file, if found, otherwise a string empty
	 */
	public static String fileRead(String path) {
		// Content to return initialization
		StringBuilder content = new StringBuilder();

		try {
			// Create a new buffered reader to read the file content
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));

			// Read each file line till the end and append it to the content StringBuilder
			String line = null;
			while ((line = reader.readLine()) != null)
				content.append(line);

			// Close the file
			reader.close();
		} catch (FileNotFoundException ex) {
			Log.e(IOUtilities.class.getName(), String.format("The file %s not exists", path));
		} catch (IOException ex) {
			Log.e(IOUtilities.class.getName(), String.format("Error during file %s reading", path));
		}

		// Return the content of the file
		return content.toString();
	}

	/**
	 * Writes the specified text value to the file
	 * @param path File path
	 * @param value The value to write
	 * @return The result of the operation (true = successful write, false = write failed)
	 */
	public static boolean fileWrite(String path, String value) {
		// If the value is null, then replace it with an empty string
		if (value == null)
			value = "";

		try {
			// Create the output stream to perform file writes
			FileOutputStream stream = new FileOutputStream(new File(path));

			// Write the passed value
			stream.write(value.getBytes());

			// Flush and close the file
			stream.flush();
			stream.close();

			// Successful write
			return true;
		} catch (FileNotFoundException e) {
			Log.e(IOUtilities.class.getName(), String.format("The file %s not exists", path));
			return false;
		} catch (IOException e) {
			Log.e(IOUtilities.class.getName(), String.format("Error during file %s writing", path));
			return false;
		}
	}

}
