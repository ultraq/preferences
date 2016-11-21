/* 
 * Copyright 2016, Emanuel Rabina (http://www.ultraq.net.nz/)
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

package nz.net.ultraq.preferences.tests

import nz.net.ultraq.preferences.Preferences

import org.junit.After
import org.junit.BeforeClass
import org.junit.Test

/**
 * Test suite for the alternate Preferences API.
 * 
 * @author Emanuel Rabina
 */
class PreferencesTests {

	private static Preferences preferences

	/**
	 * Create preferences instance to use for tests.
	 */
	@BeforeClass
	static void setupPreferences() {

		preferences = new Preferences()
	}

	/**
	 * Reset the preferences after each test.
	 */
	@After
	void clearPreferences() {

		preferences.clear()
		java.util.prefs.Preferences.userRoot().flush()
	}

	/**
	 * Clear a set preference.
	 */
	@Test
	void clearPreference() {

		// Change then clear a value
		def newValue = 'goodbye'
		preferences.set(TestUserPreferences.TEST_STRING, newValue)
		preferences.clear(TestUserPreferences.TEST_STRING)

		// Assert cleared
		def value = preferences.get(TestUserPreferences.TEST_STRING)
		assert value == TestUserPreferences.TEST_STRING.defaultValue()
	}

	/**
	 * Retrieve a default preference.
	 */
	@Test
	void getDefaultPreference() {

		def value = preferences.get(TestUserPreferences.TEST_STRING)

		assert value == TestUserPreferences.TEST_STRING.defaultValue()
	}

	/**
	 * Retrieve a preference that we've set.
	 */
	@Test
	void getPreference() {

		def newValue = 'goodbye'

		preferences.set(TestUserPreferences.TEST_STRING, newValue)
		def value = preferences.get(TestUserPreferences.TEST_STRING)

		assert value == newValue
	}
}
