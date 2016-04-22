L5 PDS Project
============

 1. install dependencies :
  - `java` `javac` : DownLoad JDK then update yout PATH
  - `postgresql` : sudo apt-get install postgresql (*P.s. [check our db config](https://github.com/L5-pds/pds-project/blob/master/buildServer/domaine/properties/config.properties)* )

 2. Compile the code :
  - `javac -encoding ISO-8859-1 -d buildServer -cp "ServerApp/lib/*" ServerApp/src/app/*.java`
  - `javac -encoding ISO-8859-1 -d buildClient -cp "ClientApp/lib/*" ClientApp/src/app/*.java`

 3. Launch the app :
  - `java -cp "buildServer:ServerApp/lib/*" app.Launcher`
  - **Start server**
  - `java -cp "buildClient:ClientApp/lib/*" app.Launcher`
