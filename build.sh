#!/bin/bash
set -e

echo "=== Car Racing J2ME Build ==="

rm -rf build
mkdir -p build/classes

# The devkit provides the pinned CLDC/MIDP APIs and ProGuard.
cd j2me-devkit

make setup

cd ..

echo "Compiling Car Racing..."

# Use the devkit's build environment.
cp -r src j2me-devkit/src/car-racing

cp manifest.mf j2me-devkit/app.jad

cd j2me-devkit

make build

cd ..

mkdir -p build

# Find the generated JAR.
JAR=$(find j2me-devkit -type f -name "*.jar" | head -n 1)

if [ -z "$JAR" ]; then
    echo "ERROR: No JAR was produced."
    exit 1
fi

cp "$JAR" build/CarRacing.jar

echo ""
echo "=== BUILD SUCCESSFUL ==="
ls -lh build/CarRacing.jar
