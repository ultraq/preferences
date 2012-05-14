
package nz.net.ultraq.preferences;

import java.util.prefs.BackingStoreException;

/**
 * Alternate entrypoint to the Preferences API.  Customized to do the following:
 * <ul>
 *   <li>enforce preference retrieval/storage with interfaces/enums</li>
 *   <li>import/export using the underlying XML file format</li>
 * </ul>
 * This is only an alternate method for accessing preferences, and does not
 * prevent one from utilizing the Preferences API in the normal manner, nor does
 * its use tie one down to my preferences implementations - if another
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
	 * Clears a single user preference.  This method is analogous to a 'reset
	 * default' function for the given preference.
	 * 
	 * @param userprefkey User preferences key to have it's value cleared.
	 */
	public static void clear(UserPreferencesKey userprefkey) {

		java.util.prefs.Preferences prefnode = userpreferences.node(
				userprefkey.getClass().getPackage().getName());
		prefnode.remove(userprefkey.name());
	}

	/**
	 * Clears a single system preference.  This method is analogous to a 'reset
	 * default' function for the given preference.
	 * 
	 * @param sysprefkey System preferences key to have it's value cleared.
	 */
	public static void clear(SystemPreferencesKey sysprefkey) {

		java.util.prefs.Preferences prefnode = systempreferences.node(
				sysprefkey.getClass().getPackage().getName());
		prefnode.remove(sysprefkey.name());
	}

	/**
	 * Clears all of the user preferences associated with the given key's
	 * package name.  This method is analogous with a 'reset defaults' function
	 * for all user preferences with the same package name.
	 * 
	 * @param userprefkey User preferences key belonging to the node of
	 * 					  preferences to clear.
	 * @throws PreferencesException If the preferences could not be cleared.
	 */
	public static void clearNode(UserPreferencesKey userprefkey) throws PreferencesException {

		try {
			java.util.prefs.Preferences prefnode = userpreferences.node(
					userprefkey.getClass().getPackage().getName());
			prefnode.clear();
		}
		catch (BackingStoreException ex) {
			throw new PreferencesException(ex.getMessage(), ex);
		}
	}

	/**
	 * Clears all of the system preferences associated with the given key's
	 * package name.  This method is analogous with a 'reset defaults' function
	 * for all system preferences with the same package name.
	 * 
	 * @param sysprefkey System preferences key belonging to the node of
	 * 					 preferences to clear.
	 * @throws PreferencesException If the preferences could not be cleared.
	 */
	public static void clearNode(SystemPreferencesKey sysprefkey) throws PreferencesException {

		try {
			java.util.prefs.Preferences prefnode = systempreferences.node(
					sysprefkey.getClass().getPackage().getName());
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
	 * @param <T> Value type.
	 * @param rootprefs One of the system or user preference root nodes.
	 * @param prefkey Preferences key.
	 * @param type	  Return type.
	 * @return The value of the preference, or the default if it doesn't exist
	 * 		   in the preferences.
	 */
	@SuppressWarnings("unchecked")
	private static <T> T get(java.util.prefs.Preferences rootprefs, PreferencesKey prefkey, Class<T> type) {

		java.util.prefs.Preferences prefnode = rootprefs.node(prefkey.getClass().getPackage().getName());
		String name = prefkey.name();
		T value = (T)prefkey.defaultValue();

		if (type == Integer.TYPE) {
			value = (T)new Integer(prefnode.getInt(name, (Integer)value));
		}
		else if (type == String.class) {
			value = (T)prefnode.get(name, (String)value);
		}

		return value;
	}

	/**
	 * Returns a user preference value.
	 * 
	 * @param userprefkey User preferences key.
	 * @return The value of the user preference, or the default if it doesn't
	 * 		   exist in the preferences.
	 */
	public static String get(UserPreferencesKey userprefkey) {

		return get(userpreferences, userprefkey, String.class);
	}

	/**
	 * Returns a system preference value.
	 * 
	 * @param sysprefkey System preferences key.
	 * @return The value of the system preference, or the default if it doesn't
	 * 		   exist in the preferences.
	 */
	public static String get(SystemPreferencesKey sysprefkey) {

		return get(systempreferences, sysprefkey, String.class);
	}

	/**
	 * Returns a preference value.
	 * 
	 * @param userprefkey User preferences key.
	 * @return The value of the user preference, or the default if it doesn't
	 * 		   exist in the preferences.
	 */
	public static int getInt(UserPreferencesKey userprefkey) {

		return get(userpreferences, userprefkey, Integer.TYPE);
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
	 * @param userprefkey User preferences key.
	 * @param value		  The value to associate with the key.
	 */
	public static void set(UserPreferencesKey userprefkey, String value) {

		java.util.prefs.Preferences prefnode = userpreferences.node(
				userprefkey.getClass().getPackage().getName());
		prefnode.put(userprefkey.name(), value);
	}

	/**
	 * Alters a system preference value.
	 * 
	 * @param sysprefkey System preferences key.
	 * @param value		 The value to associate with the key.
	 */
	public static void set(SystemPreferencesKey sysprefkey, String value) {

		java.util.prefs.Preferences prefnode = systempreferences.node(
				sysprefkey.getClass().getPackage().getName());
		prefnode.put(sysprefkey.name(), value);
	}
}
