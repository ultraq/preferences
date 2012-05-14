
package nz.net.ultraq.preferences;

/**
 * Interface outlining the format of anything that wants to retrieve a
 * preference from the {@link Preferences} class.
 * 
 * @author Emanuel Rabina
 */
interface PreferencesKey {

	/**
	 * Returns the default setting value to use if the setting key does not
	 * exist in the settings file.
	 * 
	 * @return Default setting value.
	 */
	public Object defaultValue();

	/**
	 * Returns the name of the setting/parameter to retrieve.
	 * 
	 * @return <tt>String</tt> of the key used to map the setting to.
	 */
	public String name();
}
