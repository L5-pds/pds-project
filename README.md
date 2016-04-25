L5 PDS Project
============

 1. install java, javac and postgresql (if it's not already done) :
  - `java` and `javac` : DownLoad JDK then update yout PATH
  - `postgresql` : sudo apt-get install postgresql (*P.s. [check our db config](https://github.com/L5-pds/pds-project/blob/master/buildServer/domaine/properties/config.properties)* )

 2. populate your db :
  - `psql -h your_db_host -d your_db_name -U your_db_user -f /path/to/db_script`
  - example : `psql -h 127.0.0.1 -d pds_db -U pds_user -f /pds-project/db_script.sql`

 3. Compile both server and client :
  - `chmod +x compile.sh` (assuming you're in the project directory)
  - `./compile.sh`

 4. Launch the app :
  - `java -cp "buildServer:ServerApp/lib/*" app.Launcher`
  - **Start server**
  - `java -cp "buildClient:ClientApp/lib/*" app.Launcher`
