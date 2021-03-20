#! /bin/bash
rm -rf bin/*.class
javac -cp lib/*:. -sourcepath src -d classes src/*.java -d bin/
