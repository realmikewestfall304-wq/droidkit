#!/bin/bash
#
# DroidKit Installation Script
# This script installs the DroidKit Android development library
#

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Print colored output
print_success() {
    echo -e "${GREEN}✓${NC} $1"
}

print_error() {
    echo -e "${RED}✗${NC} $1"
}

print_info() {
    echo -e "${YELLOW}ℹ${NC} $1"
}

print_header() {
    echo ""
    echo "======================================"
    echo "$1"
    echo "======================================"
    echo ""
}

# Check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Main installation function
main() {
    print_header "DroidKit Installation"
    
    # Check for required tools
    print_info "Checking system requirements..."
    
    # Check for Java
    if command_exists java; then
        JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
        print_success "Java is installed (version: $(java -version 2>&1 | head -n 1))"
    else
        print_error "Java is not installed"
        print_info "Please install Java 7 or later: https://adoptium.net/"
        exit 1
    fi
    
    # Check for Git
    if command_exists git; then
        print_success "Git is installed"
    else
        print_error "Git is not installed"
        print_info "Please install Git: https://git-scm.com/downloads"
        exit 1
    fi
    
    # Check for Gradle (optional, as gradle wrapper can be used)
    if command_exists gradle; then
        print_success "Gradle is installed"
    else
        print_info "Gradle not found globally, but that's okay - Gradle wrapper can be used"
    fi
    
    # Set installation directory
    INSTALL_DIR="${HOME}/.droidkit"
    
    print_info "Installation directory: ${INSTALL_DIR}"
    
    # Clone or update repository
    if [ -d "$INSTALL_DIR" ]; then
        print_info "DroidKit directory already exists. Updating..."
        cd "$INSTALL_DIR"
        git pull origin master || {
            print_error "Failed to update DroidKit repository"
            exit 1
        }
    else
        print_info "Cloning DroidKit repository..."
        git clone https://github.com/realmikewestfall304-wq/droidkit.git "$INSTALL_DIR" || {
            print_error "Failed to clone DroidKit repository"
            exit 1
        }
        cd "$INSTALL_DIR"
    fi
    
    print_success "DroidKit repository is ready at ${INSTALL_DIR}"
    
    # Build the library
    print_info "Building DroidKit library..."
    if [ -f "gradlew" ]; then
        chmod +x gradlew
        ./gradlew build || {
            print_error "Failed to build DroidKit"
            print_info "You may need to set up Android SDK and configure local.properties"
            print_info "See: https://developer.android.com/studio"
        }
    else
        print_info "Gradle wrapper not found. Attempting to build with system Gradle..."
        if command_exists gradle; then
            gradle build || {
                print_error "Failed to build DroidKit"
                print_info "You may need to set up Android SDK and configure local.properties"
            }
        else
            print_info "Cannot build without Gradle. Please install Gradle or use Android Studio"
        fi
    fi
    
    # Installation complete
    print_header "Installation Complete!"
    
    echo "DroidKit has been installed to: ${INSTALL_DIR}"
    echo ""
    echo "To use DroidKit in your Android project, add the following to your build.gradle:"
    echo ""
    echo "  repositories {"
    echo "      maven { url 'https://raw.github.com/elegion/maven/master/' }"
    echo "  }"
    echo ""
    echo "  dependencies {"
    echo "      implementation 'com.lightydev:droidkit:+'"
    echo "  }"
    echo ""
    echo "For more information, visit: https://github.com/realmikewestfall304-wq/droidkit"
    echo ""
    print_success "Installation complete!"
}

# Run main installation
main "$@"
