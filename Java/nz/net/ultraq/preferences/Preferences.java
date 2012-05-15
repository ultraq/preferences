
package nz.net.ultraq.preferences;

import java.util.prefs.BackingStoreException;

/**
 * Alternate entrypoint to the Preferences API.
 * <p>
 * This is only an alternate method for accessing preferences, and does not
 * prevent one from utilizing the Preferences API in the normal manner, nor does
 * its use tie one down to my XML preferences implementation - if another
 * implementation is 'higher up' in the classloading chain, then that
 * implementation will be and will actually back the calls made to this class.
 * 
 * @author Emanuel Rabina
 */
public class Preferences {

	private static final java.util.prefs.Preferences systempreferences;
	private static final java.util.prefs.Preferences userpreferences;

	static {
		systempreferences = java.util.prefs.Preferences.systemRoot();
		userpreferences   = java.util.prefs.Preferences.userRoot();
	}

	/**
	 * Hidden default constructor, as this class is only ever meant to be used
	 * statically.
	 */
	private Preferences() {
	}

	/**
	 * Clears a stored preference, allowing future calls for the preference to
	 * revert to its default value.
	 * 
	 * @param prefkey Preferences key to have it's value cleared.
	 */
	public static void clear(PreferencesKey prefkey) {

		java.util.prefs.Preferences prefnode =
				(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences)
				.node(prefkey.getClass().getPackage().getName());
		prefnode.remove(prefkey.name());
	}

	/**
	 * Clears all of the stored preferences associated with the given key's
	 * package name, reverting all those preferences to their default values.
	 * 
	 * @param prefkey Preferences key belonging to the node of preferences to
	 * 				  clear.
	 * @throws PreferencesException If the preferences could not be cleared.
	 */
	public static void clearNode(PreferencesKey prefkey) throws PreferencesException {

		try {
			java.util.prefs.Preferences prefnode =
					(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences)
					.node(prefkey.getClass().getPackage().getName());
			prefnode.clear();
		}
		catch (BackingStoreException ex) {
			throw new PreferencesException(ex.getMessage(), ex);
		}
	}

	/**
	 * Pushes any cached user preferences to the preferences' backing store.
	 * 
	 * @throws PreferencesException If the preferences could not be flushed.
	 */
	public static void flushUser() throws PreferencesException {

		try {
			userpreferences.flush();
		}
		catch (BackingStoreException ex) {
			throw new PreferencesException(ex.getMessage(), ex);
		}
	}

	/**
	 * Pushes any cached system preferences to the preferences' backing store.
	 * 
	 * @throws PreferencesException If the preferences could not be flushed.
	 */
	public static void flushSystem() throws PreferencesException {

		try {
			systempreferences.flush();
		}
		catch (BackingStoreException ex) {
			throw new PreferencesException(ex.getMessage(), ex);
		}
	}

	/**
	 * Returns a preference value.
	 * 
	 * @param rootprefs One of the system or user preference root nodes.
	 * @param prefkey	Preferences key.
	 * @return The value of the preference, or the default if it doesn't exist
	 * 		   in the preferences.
	 */
	private static Object get(java.util.prefs.Preferences rootprefs, PreferencesKey prefkey) {

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
	public static String get(PreferencesKey prefkey) {

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
	public static boolean getBoolean(PreferencesKey prefkey) {

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
	public static int getInt(PreferencesKey prefkey) {

		return (Integer)get(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey);
	}

	/**
	 * Return whether or not the node for this key class exists.
	 * 
	 * @param keyclass
	 * @return <tt>true</tt> if the node housing the given key class exists.
	 * @throws PreferencesException If there was some error in communicating
	 * 		   with the backing store.
	 */
	public static boolean nodeExists(Class<? extends PreferencesKey> keyclass) throws PreferencesException {

		try {
			return systempreferences.nodeExists(keyclass.getPackage().getName());
		}
		catch (BackingStoreException ex) {
			throw new PreferencesException(ex.getMessage(), ex);
		}
	}

	/**
	 * Alters a user preference value.
	 * 
	 * @param rootprefs One of the system or user preferences.
	 * @param prefkey	Preferences key.
	 * @param value		The value to associate with the key.
	 */
	private static void set(java.util.prefs.Preferences rootprefs, PreferencesKey prefkey, Object value) {

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
	}

	/**
	 * Sets a preference value.
	 * 
	 * @param prefkey Preferences key, an instance of {@link UserPreferencesKey}
	 * 				  or {@link SystemPreferencesKey}.
	 * @param value	  The value to associate with the key.
	 */
	public static void set(PreferencesKey prefkey, String value) {

		set(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey, value);
	}

	/**
	 * Sets a preference value.
	 * 
	 * @param prefkey Preferences key, an instance of {@link UserPreferencesKey}
	 * 				  or {@link SystemPreferencesKey}.
	 * @param value	  The value to associate with the key.
	 */
	public static void setBoolean(PreferencesKey prefkey, boolean value) {

		set(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey, value);
	}

	/**
	 * Sets a preference value.
	 * 
	 * @param prefkey Preferences key, an instance of {@link UserPreferencesKey}
	 * 				  or {@link SystemPreferencesKey}.
	 * @param value	  The value to associate with the key.
	 */
	public static void setInt(PreferencesKey prefkey, int value) {

		set(prefkey instanceof UserPreferencesKey ? userpreferences : systempreferences, prefkey, value);
	}
}
