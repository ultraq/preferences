
Preferences
===========

[![Build Status](https://github.com/ultraq/preferences/actions/workflows/build.yml/badge.svg)](https://github.com/ultraq/preferences/actions)
[![codecov](https://codecov.io/gh/ultraq/preferences/branch/main/graph/badge.svg?token=hAt037wG33)](https://codecov.io/gh/ultraq/preferences)
[![Maven Central](https://img.shields.io/maven-central/v/nz.net.ultraq.preferences/preferences.svg?maxAge=3600)](http://search.maven.org/#search|ga|1|g%3A%22nz.net.ultraq.preferences%22%20AND%20a%3A%22preferences%22)

A simplified and typesafe entry point to the Java Preferences API (`java.util.prefs`).
I wrote this library out of my wish to use the then-new Java 5 feature of `enum`'s
as keys for preference values instead of `String` constants like most people
were doing before Java 5 days.


Installation
------------

Minimum of Java 8 required.

### For Maven and Maven-compatible dependency managers

Add a dependency to your project with the following co-ordinates:

 - GroupId: `nz.net.ultraq.preferences`
 - ArtifactId: `preferences`
 - Version: `3.1.0`

Check the [project releases](https://github.com/ultraq/preferences/releases)
for a list of available versions.  Each release page also includes a
downloadable JAR if you want to manually add it to your project classpath.


Usage
-----

Implement `enum`s that extend from `UserPreferencesKey` or
`SystemPreferencesKey` to create user/system preferences respectively.  Then,
use those enum values over an instance of `nz.net.ultraq.preferences.Preferences`
to load/save the relevant preferences.  eg:

```java
public enum MyPreferences implements UserPreference {

  WINDOW_WIDTH  (800),
  WINDOW_HEIGHT (600);

  public final Object defaultValue;

  private MyPreferences(Object defaultValue) {
    this.defaultValue = defaultValue;
  }

  @Override
  public Object getDefaultValue() {
    return defaultValue;
  }
}

public class MyClass {

  public static void main(String[] args) {

    Preferences preferences = new Preferences();

    int windowWidth = preferences.get(MyPreferences.WINDOW_WIDTH);
    int windowHeight = preferences.get(MyPreferences.WINDOW_HEIGHT);

    preferences.set(MyPreferences.WINDOW_WIDTH, 1024);
  }
}
```


API
---

Browse the online groovydocs for this library here:
https://javadoc.io/doc/nz.net.ultraq.preferences/preferences
