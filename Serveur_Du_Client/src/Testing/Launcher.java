package Testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Launcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Interface_Serveur launch_IHM = new Interface_Serveur();
		launch_IHM.setVisible(true);
		
	}

}
