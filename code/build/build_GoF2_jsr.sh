#!/bin/bash

CODE_DIR=$(realpath $BASH_SOURCE | xargs dirname | xargs dirname)

if [ -z "$CODE_DIR" ]
then
      echo "\$CODE_DIR is empty"
      exit 2
fi

echo detected project folder $CODE_DIR

if [ -z "$JDK_HOME" ]
then
    echo JDK_HOME not provided, using default one
    JDK_HOME="D:/program files (x86)/Java/jdk1.8.0_172/"
fi

MIDLET_NAME=GalaxyOnFire2_jsr

MIDLET_TMPCLASSES=$CODE_DIR/build/target/tmpclasses
MIDLET_CLASSES=$CODE_DIR/build/target/classes
MIDLET_PROJECT=$CODE_DIR/GoF2_jsr
TARGET_JAR=$CODE_DIR/build/target/$MIDLET_NAME.jar

LIBS=$(find $CODE_DIR/build/libs -name "*.jar" -printf "%p:" | sed 's/:$//')

echo creating folders

rm -rf $MIDLET_TMPCLASSES
rm -rf $MIDLET_CLASSES
rm $TARGET_JAR

mkdir -p $MIDLET_TMPCLASSES
if [[ $? -ne 0 ]] ; then
    exit 1
fi

mkdir -p $MIDLET_CLASSES
if [[ $? -ne 0 ]] ; then
    exit 1
fi

echo compiling


"$JDK_HOME/bin/javac" -source 1.3 -target 1.1 -g:none -bootclasspath $LIBS -d $MIDLET_TMPCLASSES -classpath $MIDLET_CLASSES $(find "$MIDLET_PROJECT/src/" -name "*.java") -Xlint:-options -encoding UTF-8 
if [[ $? -ne 0 ]] ; then
    exit 1
fi

echo preverify

if [[ "$OSTYPE" == "msys"* || "$OSTYPE" == "cygwin"* || "$OS" == "Windows_NT" ]]; then
    PREVERIFY="$CODE_DIR/build/tools/preverify.exe"
else
    PREVERIFY="$CODE_DIR/build/tools/preverify"
fi
$PREVERIFY -classpath $LIBS:$MIDLET_TMPCLASSES -d $MIDLET_CLASSES $MIDLET_TMPCLASSES
if [[ $? -ne 0 ]] ; then
    exit 1
fi

echo packing jar
"$JDK_HOME/bin/jar" cmf $MIDLET_PROJECT/MANIFEST.MF $TARGET_JAR -C $MIDLET_CLASSES . -C $MIDLET_PROJECT/res  .
if [[ $? -ne 0 ]] ; then
    exit 1
fi