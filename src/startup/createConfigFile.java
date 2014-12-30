package startup;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class createConfigFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Properties prop = new Properties();
	try{
		new FileInputStream("Config.properties");
	}catch(Exception e){
	prop.setProperty("DBConnectionString","jdbc:mysql://localhost:3307/fileupload");
	prop.setProperty("Driver","com.mysql.jdbc.Driver");
	prop.setProperty("Username","root");
	prop.setProperty("Password","redhat");
	prop.setProperty("step","5");
	prop.store(new FileOutputStream("Config.properties"),null);
	}
	}
}