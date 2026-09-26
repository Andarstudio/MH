#!/bin/bash

set -e

echo "=== Car Racing J2ME Build ==="

rm -rf build
mkdir -p build/classes

# Copy the J2ME libraries from the template
mkdir -p sdk/lib

find j2me -name "cldcapi10.jar" -exec cp {} sdk/lib/cldcapi10.jar \; -quit
find j2me -name "midpapi20.jar" -exec cp {} sdk/lib/midpapi20.jar \; -quit

if [ ! -f sdk/lib/cldcapi10.jar ]; then
    echo "ERROR: cldcapi10.jar not found"
    exit 1
fi

if [ ! -f sdk/lib/midpapi20.jar ]; then
    echo "ERROR: midpapi20.jar not found"
    exit 1
fi

echo "J2ME libraries found."

echo "Compiling source..."

javac \
  -source 1.3 \
  -target 1.1 \
  -bootclasspath "sdk/lib/cldcapi10.jar:sdk/lib/midpapi20.jar" \
  -d build/classes \
  src/CarRacing.java \
  src/GameCanvas.java \
  src/PlayerCar.java \
  src/TrafficCar.java \
  src/GameManager.java

echo "Creating JAR..."

jar cfm build/CarRacing.jar manifest.mf \
  -C build/classes .

echo "================================"
echo "BUILD COMPLETE"
echo "================================"

ls -lh build/CarRacing.jar
