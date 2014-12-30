package classes;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class setconfigproperties extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public setconfigproperties() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	System.out.println("in setconfig properties servlet");
	String step = request.getParameter("step");
	Properties prop = new Properties();
	prop.setProperty("DBConnectionString","jdbc:mysql://localhost:3307/fileupload");
	prop.setProperty("Driver","com.mysql.jdbc.Driver");
	prop.setProperty("Username","root");
	prop.setProperty("Password","redhat");
	prop.setProperty("step",step);
	prop.store(new FileOutputStream("Config.properties"),null);
	request.getSession().setAttribute("step",Integer.parseInt(step));
	//RequestDispatcher view = request.getRequestDispatcher("redirector");
	//view.forward(request,response);
	}
}