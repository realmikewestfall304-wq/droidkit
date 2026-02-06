/**
 * OpenClaw (DroidKit) - Android Development Kit
 * 
 * Main entry point for the openclaw npm package.
 * 
 * This package provides information about the DroidKit Android library.
 * For Android development, use the Gradle dependency in your Android project.
 */

module.exports = {
  name: 'OpenClaw',
  version: require('./package.json').version,
  description: 'Android Development Kit - DroidKit library',
  
  info: function() {
    return {
      name: 'DroidKit',
      packageName: 'openclaw',
      version: this.version,
      androidLibrary: true,
      features: [
        'Async Image Loading',
        'Async Network utilities',
        'Android API 14+ support'
      ],
      gradle: {
        repository: "maven { url 'https://raw.github.com/elegion/maven/master/' }",
        dependency: "implementation 'com.lightydev:droidkit:+'"
      }
    };
  }
};
