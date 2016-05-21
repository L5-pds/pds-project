#!/bin/bash

#server & client compilation
mkdir build
printf "\33[38;5;190m%s\33[0m\n" "start compilation..."
javac -encoding ISO-8859-1 -d build -cp "build:pdsApp/lib/*" pdsApp/src/app/listeners/*.java pdsApp/src/app/models/*.java pdsApp/src/app/models/component/*.java pdsApp/src/app/helpers/*.java pdsApp/src/app/controllers/*.java pdsApp/src/app/views/**/*.java pdsApp/src/app/*.java
printf "\33[32;5;32m%s\33[0m\n" "done!"
