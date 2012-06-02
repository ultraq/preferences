
Preferences
===========

An alternate entry-point to the Java Preferences API (java.util.prefs) for
type-safe application preferences.  I wrote this library out of my wish to use
the then-new Java 5 feature of `enum`'s as keys for preference values instead of
String constants like most people were doing before Java 5 days.

Precompiled JAR downloads available in the [Downloads](preferences/downloads) section.


Requirements
------------

 - Java 6


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
