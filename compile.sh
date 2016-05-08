#!/bin/bash

#server & client compilation
mkdir build
printf "\33[38;5;190m%s\33[0m\n" "start compilation..."
javac -encoding ISO-8859-1 -d build -cp "build:pdsApp/lib/*" pdsApp/src/app/listeners/*.java
javac -encoding ISO-8859-1 -d build -cp "build:pdsApp/lib/*" pdsApp/src/app/models/*.java
javac -encoding ISO-8859-1 -d build -cp "build:pdsApp/lib/*" pdsApp/src/app/helpers/*.java
javac -encoding ISO-8859-1 -d build -cp "build:pdsApp/lib/*" pdsApp/src/app/controllers/*.java
javac -encoding ISO-8859-1 -d build -cp "build:pdsApp/lib/*" pdsApp/src/app/views/welcome/*.java
javac -encoding ISO-8859-1 -d build -cp "build:pdsApp/lib/*" pdsApp/src/app/*.java
printf "\33[32;5;32m%s\33[0m\n" "done!"
