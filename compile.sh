#!/bin/bash

#server compilation
printf "\33[38;5;190m%s\33[0m\n" "server compilation..."
javac -encoding ISO-8859-1 -d buildServer -cp "ServerApp/lib/*" ServerApp/src/app/*.java
printf "\33[32;5;32m%s\33[0m\n" "done!"

#client compilation
printf "\33[38;5;190m%s\33[0m\n" "client compilation..."
javac -encoding ISO-8859-1 -d buildClient -cp "buildClient:ClientApp/lib/*" ClientApp/src/app/models/*.java
javac -encoding ISO-8859-1 -d buildClient -cp "buildClient:ClientApp/lib/*" ClientApp/src/app/helpers/*.java
javac -encoding ISO-8859-1 -d buildClient -cp "buildClient:ClientApp/lib/*" ClientApp/src/app/controllers/*.java
javac -encoding ISO-8859-1 -d buildClient -cp "buildClient:ClientApp/lib/*" ClientApp/src/app/views/welcome/*.java
javac -encoding ISO-8859-1 -d buildClient -cp "buildClient:ClientApp/lib/*" ClientApp/src/app/*.java
printf "\33[32;5;32m%s\33[0m\n" "done!"
