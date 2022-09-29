#!/bin/bash
set -e

DIR="$2"
if ! [ -d $DIR/node_modules/react-native-mo-android-activity ]; then
  echo "$DIR/node_modules/react-native-mo-android-activity not there" 1>&2
  exit 1
fi

if [ "$1" == "from" ]; then
  cp -r $DIR/node_modules/react-native-mo-android-activity/{readme.md,src} .
  cp -r $DIR/node_modules/react-native-mo-android-activity/android/{src,build.gradle} ./android/
fi

if [ "$1" == "to" ]; then
  rsync -a --exclude node_modules --exclude example --exclude .git . $DIR/node_modules/react-native-mo-android-activity/
fi
