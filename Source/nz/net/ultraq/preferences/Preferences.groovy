/* 
 * Copyright 2012, Emanuel Rabina (http://www.ultraq.net.nz/)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nz.net.ultraq.preferences

/**
 * Alternate entrypoint to the Preferences API.
 * <p>
 * This is only an alternate method for accessing preferences and does not
 * prevent one from utilizing the Preferences API in the normal manner.
 * 
 * @author Emanuel Rabina
 */
class Preferences {

	private final java.util.prefs.Preferences systempreferences = java.util.prefs.Preferences.systemRoot();
	private final java.util.prefs.Preferences userpreferences   = java.util.prefs.Preferences.userRoot();

	/**
	 * Clears a stored preference, allowing future calls for the preference to
	 * revert to its default value.
	 * 
	 * @param prefkey Preferences key to have it's value cleared.
	 */
	void clear(PreferencesKey prefkey) {

		java.util.prefs.Preferences prefnode =
				(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences)
				.node(prefkey.getClass().getPackage().getName());
		prefnode.remove(prefkey.name());
	}

	/**
	 * Clears all of the stored preferences associated with the given key's
	 * package name, reverting all those preferences to their default values.
	 * 
	 * @param prefkey Preferences key belonging to the package of preferences to
	 * 				  clear.
	 */
	void clearPackage(PreferencesKey prefkey) {

		java.util.prefs.Preferences prefnode =
				(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences)
				.node(prefkey.getClass().getPackage().getName());
		prefnode.clear();
	}

	/**
	 * Pushes any cached preferences to the preferences' backing store.
	 */
	void flushPreferences() {

		userpreferences.flush();
		systempreferences.flush();
	}

	/**
	 * Returns a preference value.
	 * 
	 * @param rootprefs One of the system or user preference root nodes.
	 * @param prefkey	Preferences key.
	 * @return The value of the preference, or the default if it doesn't exist
	 * 		   in the preferences.
	 */
	Object get(java.util.prefs.Preferences rootprefs, PreferencesKey prefkey) {

		java.util.prefs.Preferences prefnode = rootprefs.node(prefkey.getClass().getPackage().getName());
		String key = prefkey.name();
		Object value = prefkey.defaultValue();
		Class<?> type = value.getClass();

		if (type == Boolean.class) {
			value = prefnode.getBoolean(key, (Boolean)value);
		}
		else if (type == Integer.class) {
			value = prefnode.getInt(key, (Integer)value);
		}
		else if (type == String.class) {
			value = prefnode.get(key, (String)value);
		}
		else {
			byte[] objectbytes = prefnode.getByteArray(key, [] as byte[]);
			if (objectbytes.length != 0) {
				value = new ObjectInputStream(new ByteArrayInputStream(objectbytes)).readObject();
			}
		}

		return value;
	}

	/**
	 * Returns a preference value.
	 * 
	 * @param prefkey Preferences key, an instance of {@link UserPreferencesKey}
	 * 				  or {@link SystemPreferencesKey}.
	 * @return The value of the preference, or the default if it doesn't exist
	 * 		   in the preferences.
	 */
	String get(PreferencesKey prefkey) {

		return (String)get(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey);
	}

	/**
	 * Returns a preference value.
	 * 
	 * @param prefkey Preferences key, an instance of {@link UserPreferencesKey}
	 * 				  or {@link SystemPreferencesKey}.
	 * @return The value of the preference, or the default if it doesn't exist
	 * 		   in the preferences.
	 */
	boolean getBoolean(PreferencesKey prefkey) {

		return (Boolean)get(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey);
	}

	/**
	 * Returns a preference value.
	 * 
	 * @param prefkey Preferences key, an instance of {@link UserPreferencesKey}
	 * 				  or {@link SystemPreferencesKey}.
	 * @return The value of the preference, or the default if it doesn't exist
	 * 		   in the preferences.
	 */
	Object getObject(PreferencesKey prefkey) {

		return get(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey);
	}

	/**
	 * Returns a preference value.
	 * 
	 * @param prefkey Preferences key, an instance of {@link UserPreferencesKey}
	 * 				  or {@link SystemPreferencesKey}.
	 * @return The value of the preference, or the default if it doesn't exist
	 * 		   in the preferences.
	 */
	int getInt(PreferencesKey prefkey) {

		return (Integer)get(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey);
	}

	/**
	 * Return whether or not the given preference is set in the backing store.
	 * 
	 * @param prefkey Preference to check.
	 * @return <tt>true</tt> if the preference has actually been set in the
	 * 		   backing store.
	 */
	boolean preferenceExists(final PreferencesKey prefkey) {

		// Create a dummy preference key to check against
		PreferencesKey dummypref = new PreferencesKey() {
			@Override
			public String defaultValue() {
				return prefkey.defaultValue().toString() + "-";
			}
			@Override
			public String name() {
				return prefkey.name();
			}
		};
		Object value = get(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, dummypref);
		return !value.equals(dummypref.defaultValue());
	}

	/**
	 * Alters a user preference value.
	 * 
	 * @param rootprefs One of the system or user preferences.
	 * @param prefkey	Preferences key.
	 * @param value		The value to associate with the key.
	 */
	void set(java.util.prefs.Preferences rootprefs, PreferencesKey prefkey, Object value) {

		java.util.prefs.Preferences prefnode = rootprefs.node(prefkey.getClass().getPackage().getName());
		String key = prefkey.name();
		Class<?> type = value.getClass();

		if (type == Boolean.class) {
			prefnode.putBoolean(key, (Boolean)value);
		}
		else if (type == Integer.class) {
			prefnode.putInt(key, (Integer)value);
		}
		else if (type == String.class) {
			prefnode.put(key, (String)value);
		}
		else {
			ByteArrayOutputStream objectbytes = new ByteArrayOutputStream();
			new ObjectOutputStream(objectbytes).writeObject(value);
			prefnode.putByteArray(key, objectbytes.toByteArray());
		}
	}

	/**
	 * Sets a preference value.
	 * 
	 * @param prefkey Preferences key, an instance of {@link UserPreferencesKey}
	 * 				  or {@link SystemPreferencesKey}.
	 * @param value	  The value to associate with the key.
	 */
	void set(PreferencesKey prefkey, String value) {

		set(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey, value);
	}

	/**
	 * Sets a preference value.
	 * 
	 * @param prefkey Preferences key, an instance of {@link UserPreferencesKey}
	 * 				  or {@link SystemPreferencesKey}.
	 * @param value	  The value to associate with the key.
	 */
	void setBoolean(PreferencesKey prefkey, boolean value) {

		set(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey, value);
	}

	/**
	 * Sets a preference value.
	 * 
	 * @param prefkey Preferences key, an instance of {@link UserPreferencesKey}
	 * 				  or {@link SystemPreferencesKey}.
	 * @param value	  The value to associate with the key.
	 */
	void setInt(PreferencesKey prefkey, int value) {

		set(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey, value);
	}

	/**
	 * Sets a preference value.
	 * 
	 * @param prefkey Preferences key, an instance of {@link UserPreferencesKey}
	 * 				  or {@link SystemPreferencesKey}.
	 * @param value	  The value to associate with the key.
	 */
	void setObject(PreferencesKey prefkey, Object value) {

		set(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey, value);
	}
}
