
package nz.net.ultraq.preferences;

/**
 * Implementation of the <tt>PreferencesFactory</tt> interface of the
 * Preferences API, returns the {@link PreferencesImpl} implementation in place
 * of the Java runtime default.
 * <p>
 * To use this implementation of the Preferences API, the application name must
 * be a system property mapped to the following key:
 * <tt>nz.net.ultraq.common.preferences.appname</tt>
 * 
 * @author Emanuel Rabina
 * @version 1.0
 */
public class PreferencesFactory implements java.util.prefs.PreferencesFactory {

	// System property key used to map to the currently-running application name
	public static final String PREFERENCES_APP_NAME = "nz.net.ultraq.common.preferences.appname";
	private static final String USER_NAME = "user.name";

	private static String appname;
	private static String username;

	// Root system/user nodes
	private static PreferencesImpl SYSTEM_ROOT;
	private static PreferencesImpl USER_ROOT;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized java.util.prefs.Preferences systemRoot() throws PreferencesException {

		if (SYSTEM_ROOT == null) {
			SYSTEM_ROOT = new PreferencesImpl(resolveApplicationName(), null);
		}
		return SYSTEM_ROOT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized java.util.prefs.Preferences userRoot() throws PreferencesException {

		if (USER_ROOT == null) {
			USER_ROOT = new PreferencesImpl(resolveApplicationName(), resolveUserName());
		}
		return USER_ROOT;
	}

	/**
	 * Returns a modified application name system property.
	 * 
	 * @return The system property mapped to {@link #PREFERENCES_APP_NAME}.
	 * @throws PreferencesException If the application name system property has
	 * 		   not been set.
	 */
	private static String resolveApplicationName() throws PreferencesException {

		if (appname == null) {
			appname = System.getProperty(PREFERENCES_APP_NAME);
			if (appname == null) {
				throw new PreferencesException("The " + PREFERENCES_APP_NAME + " system property " +
						"must be set to the name of the currently-running application to use this " +
						"implementation of the Preferences API.");
			}
			appname = appname.replace(" ", "").toLowerCase();
		}
		return appname;
	}

	/**
	 * Returns a modified user name system property.
	 * 
	 * @return The username of the currently logged-in user.
	 */
	private static String resolveUserName() {

		if (username == null) {
			username = System.getProperty(USER_NAME);
			username = username.replace(" ", "").toLowerCase();
		}
		return username;
	}
}
