package classes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
public Connection getConnection() throws FileNotFoundException, IOException {
	Properties prop = new Properties();
	Connection conn = null;
	try{
		new FileInputStream("Config.properties");
	}catch(Exception e)
	{
		prop.setProperty("DBConnectionString","jdbc:mysql://localhost:3307/fileupload");
		prop.setProperty("Driver","com.mysql.jdbc.Driver");
		prop.setProperty("Username","root");
		prop.setProperty("Password","redhat");
		prop.setProperty("step","5");
		prop.store(new FileOutputStream("Config.properties"),null);
		}
	try{
	prop.load(new FileInputStream("Config.properties"));
	String DBConnectionString = prop.getProperty("DBConnectionString");
	String Driver = prop.getProperty("Driver");
	String Username = prop.getProperty("Username");
	String Password = prop.getProperty("Password");
	Class.forName(Driver);
	conn = DriverManager.getConnection(DBConnectionString,Username,Password);
	}catch(Exception e)
	{
		System.out.println("Exception Found in DBConnection servlet : " + e);
	}
return conn;
}
}