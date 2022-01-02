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

package nz.net.ultraq.preferences

import static TestPreferences.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test suite for the alternate Preferences API.
 * 
 * @author Emanuel Rabina
 */
class PreferencesTests {

	private Preferences preferences

	/**
	 * Create a fresh preferences instance to use for tests.
	 */
	@BeforeEach
	void setupPreferences() {

		preferences = new Preferences()
		preferences.clear()
	}

	/**
	 * Clear a set preference.
	 */
	@Test
	void clearPreference() {

		// Change then clear a value
		preferences.set(TEST_STRING_CLEAR, 'something else')
		preferences.clear(TEST_STRING_CLEAR)

		// Assert cleared
		def result = preferences.get(TEST_STRING_CLEAR)
		assert result == TEST_STRING_CLEAR.defaultValue
	}

	/**
	 * Retrieve a default preference.
	 */
	@Test
	void getDefaultPreference() {

		def result = preferences.get(TEST_STRING_DEFAULT)
		assert result == TEST_STRING_DEFAULT.defaultValue
	}

	/**
	 * Retrieve a preference that we've set.
	 */
	@Test
	void getPreference() {

		def newValue = 'goodbye'
		preferences.set(TEST_STRING_GET, newValue)

		def result = preferences.get(TEST_STRING_GET)
		assert result == newValue
	}
}
