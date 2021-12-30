
Changelog
=========

### 2.1.0
 - Updated build scripts and moved to GitHub Actions from Travis CI.

### 2.0.0
 - Project rewritten in Groovy
 - Minimum Java version upped to 8
 - Preferences object is no longer full of static methods, you now need to
   create an instance of one to use its methods.
 - Unit tests added!
 - Greatly simplified the public API by removing methods that are now redundant
   and making other methods automatic thus no longer requiring developer input.

### 1.2.2
 - Minor fixes from the updated [maven-support](https://github.com/ultraq/gradle-support)
   Gradle script.
 - Change of 'preference exists' check instead of 'package exists' check since
   exposing package reveals the node structure (and I'm trying to hide that with
   this library).

### 1.2.1
 - Switched from Ant to Gradle as a build tool.
 - Made project available from Maven Central.

### 1.2
 - Initial GitHub version.
