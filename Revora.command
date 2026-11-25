#!/bin/bash

cd "$(dirname "$0")" || exit 1

echo "================================"
echo "       REVORA APPLICATION"
echo "================================"

# Check Java installation
if ! command -v javac >/dev/null 2>&1; then
    echo "Java JDK is not installed!"
    echo "Install using: brew install openjdk"
    read -r -p "Press Enter to exit..."
    exit 1
fi

# Clean previous compilation
echo "Cleaning bin folder..."
rm -rf bin
mkdir -p bin

# Find Java files
echo "Finding Java files..."
find ./src -type f -name "*.java" > sources.txt

if [ ! -s sources.txt ]; then
    echo "ERROR: No Java files found inside src!"
    read -r -p "Press Enter to exit..."
    exit 1
fi

# Compile
echo "Compiling Revora..."
javac -encoding UTF-8 -d bin @sources.txt

if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    read -r -p "Press Enter to exit..."
    exit 1
fi

# Run application
echo "Starting Revora..."
java -cp bin revora.Main

read -r -p "Press Enter to exit..."