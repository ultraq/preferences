/* 
 * Copyright 2007, Emanuel Rabina (http://www.ultraq.net.nz/)
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
 * Interface outlining the format of anything that wants to retrieve a
 * preference from the {@link Preferences} class.  Used to make type-safe
 * preferences retrieval using enums.
 * 
 * @author Emanuel Rabina
 */
interface Preference {

	/**
	 * Returns the default value to use if the preference has no value yet.
	 * 
	 * @return Default value.
	 */
	Object getDefaultValue()

	/**
	 * Returns the name of the preference.  The Java {@code enum} type already has
	 * this method, so implementations won't need to do anything for this method.
	 * 
	 * @return <tt>String</tt> of the key used to map the setting to.
	 */
	String name()
}
