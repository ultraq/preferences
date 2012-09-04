
Preferences
===========

An alternate entry-point to the Java Preferences API (java.util.prefs) for
type-safe application preferences.  I wrote this library out of my wish to use
the then-new Java 5 feature of `enum`'s as keys for preference values instead of
String constants like most people were doing before Java 5 days.


Requirements
------------

 - Java 6


Installation
------------

### Standalone distribution
Download a copy of of the pre-compiled JAR from [the Downloads section](preferences/downloads)
or build the project from the source code here on GitHub.

### For Maven and Maven-compatible dependency managers
Add a dependency to your project with the following co-ordinates:

 - GroupId: `nz.net.ultraq.preferences`
 - ArtifactId: `preferences`
 - Version: `1.2.2`


Usage
-----

Use the static methods of the `nz.net.ultraq.preferences.Preferences` class to
access the Java Preferences API. To get/set values, write `enum`s that implement
the `UserPreferencesKey` or `SystemPreferencesKey` interfaces for user or system
-scope preferences respectively, then use the get/set methods using your `enum`
classes.

	public enum MyPreferences implements UserPreferencesKey {
	
		WINDOW_WIDTH  (800),
		WINDOW_HEIGHT (600);

		public final Object defaultValue;
		
		private MyPreferences(Object defaultValue) {
			this.defaultValue = defaultValue;
		}
	
		@Override
		public Object defaultValue() {
			return defaultValue;
		}
	}
	
	public class MyClass {
	
		public static void main(String[] args) {
	
			int windowWidth = Preferences.getInt(MyPreferences.WINDOW_WIDTH);
			int windowHeight = Preferences.getInt(MyPreferences.WINDOW_HEIGHT);
	
			Preferences.setInt(MyPreferences.WINDOW_WIDTH, 1024);
			Preferences.flush();
		}
	}

I usually use this library in-conjunction with my [Preferences - XML](https://github.com/ultraq/preferences-xml)
project, which will write user/system preferences to an XML file in a
subdirectory of your project, rather than using the default implementation
which, on Windows, uses the registry as the backing store.


Changelog
---------

### 1.2.2
 - Minor fixes from the updated [maven-support](https://github.com/ultraq/gradle-support)
   Gradle script.
 - Change of 'preference exists' check instead of 'package exists' check since
   exposing package reveals the node structure (and I'm trying to hide that with
   this library).

### 1.2.1
 - Switched from Ant to Gradle as a build tool.
 - Made project available from Maven Central.  Maven co-ordinates added to the
   [Installation](#installation) section.

### 1.2
 - Initial GitHub version.

