#!/usr/bin/env node

/**
 * OpenClaw CLI - DroidKit Android Development Kit
 * 
 * This is a command-line interface for the DroidKit Android library.
 */

const path = require('path');
const fs = require('fs');

const packageJson = require('../package.json');

function showHelp() {
  console.log(`
OpenClaw (DroidKit) v${packageJson.version}
Android Development Kit

Usage:
  openclaw [command] [options]

Commands:
  help      Show this help message
  version   Show version information
  info      Display library information

Description:
  DroidKit is an Android development library providing:
  - Async Image Loading with DkImageView
  - Async Network operations with Http utilities

For more information, visit:
  https://github.com/realmikewestfall304-wq/droidkit
`);
}

function showVersion() {
  console.log(`OpenClaw v${packageJson.version}`);
}

function showInfo() {
  console.log(`
OpenClaw (DroidKit) - Android Development Kit
Version: ${packageJson.version}
License: ${packageJson.license}

Installation for Android projects:
  With gradle:
    repositories {
        maven { url 'https://raw.githubusercontent.com/elegion/maven/master/' }
    }

    dependencies {
        implementation 'com.lightydev:droidkit:+'
    }

Features:
  - Async Image Loading
  - Async Network utilities
  - Support for Android API 14+
`);
}

// Main CLI logic
const args = process.argv.slice(2);
const command = args[0];

switch (command) {
  case 'version':
  case '--version':
  case '-v':
    showVersion();
    break;
  case 'info':
    showInfo();
    break;
  case 'help':
  case '--help':
  case '-h':
  case undefined:
    showHelp();
    break;
  default:
    console.error(`Unknown command: ${command}`);
    console.log('Run "openclaw help" for usage information.');
    process.exit(1);
}
