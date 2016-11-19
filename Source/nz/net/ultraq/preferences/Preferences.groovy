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

	private final java.util.prefs.Preferences systemPreferences = java.util.prefs.Preferences.systemRoot()
	private final java.util.prefs.Preferences userPreferences   = java.util.prefs.Preferences.userRoot()

	/**
	 * Clears a stored preference, allowing future calls for the preference to
	 * revert to its default value.
	 * 
	 * @param preferencesKey Key to have it's value cleared.
	 */
	void clear(PreferencesKey preferencesKey) {

		def preferencesNode =
			(preferencesKey instanceof UserPreferencesKey ? userPreferences : systemPreferences)
				.node(preferencesKey.class.package.name)
		preferencesNode.remove(preferencesKey.name())
	}

	/**
	 * Ensures that any cached preferences are pushed to the backing store.
	 */
	protected void finalize() {

		userPreferences.flush()
		systemPreferences.flush()
	}

	/**
	 * Returns the value for the given preference.
	 * 
	 * @param preferencesKey Preferences key.
	 * @return The value of the preference, or the default value if it hasn't been
	 *         overidden with another value.
	 */
	@SuppressWarnings('UnnecessaryPublicModifier') // Needed for the parser to understand the generic type
	public <T> T get(PreferencesKey preferencesKey) {

		def preferencesNode =
			(preferencesKey instanceof UserPreferencesKey ? userPreferences : systemPreferences)
			.node(preferencesKey.class.package.name)
		def key = preferencesKey.name()
		def value = preferencesKey.defaultValue()

		if (value instanceof String) {
			value = preferencesNode.get(key, value)
		}
		else if (value instanceof Boolean) {
			value = preferencesNode.getBoolean(key, value)
		}
		else if (value instanceof Integer) {
			value = preferencesNode.getInt(key, value)
		}
		else if (value instanceof byte[]) {
			def bytes = preferencesNode.getByteArray(key, value)
			if (bytes.length) {
				value = new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject()
			}
		}

		return value
	}

	/**
	 * Sets a value for the given preference.
	 * 
	 * @param preferencesKey Preferences key.
	 * @param value          The value to associate with the key.
	 */
	void set(PreferencesKey preferencesKey, Object value) {

		def preferencesNode =
			(preferencesKey instanceof UserPreferencesKey ? userPreferences : systemPreferences)
			.node(preferencesKey.class.package.name)
		def key = preferencesKey.name()

		if (value instanceof String) {
			preferencesNode.put(key, value)
		}
		else if (value instanceof Boolean) {
			preferencesNode.putBoolean(key, value)
		}
		else if (value instanceof Integer) {
			preferencesNode.putInt(key, value)
		}
		else if (value instanceof byte[]) {
			def bytes = new ByteArrayOutputStream()
			new ObjectOutputStream(bytes).writeObject(value)
			preferencesNode.putByteArray(key, bytes.toByteArray())
		}
	}
}
