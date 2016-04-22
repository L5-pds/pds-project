L5 PDS Project
============

 1. install `java` and `javac` command
  - DownLoad JDK then update yout PATH

 2. Compile the code :
  - `javac -encoding ISO-8859-1 -d buildServer -cp "ServerApp/lib/*" ServerApp/src/app/*.java`
  - `javac -encoding ISO-8859-1 -d buildClient -cp "ClientApp/lib/*" ClientApp/src/app/*.java`

 3. Launch the app :
  - `java -cp buildServer app.Launcher`
  - `java -cp buildClient app.Launcher`
