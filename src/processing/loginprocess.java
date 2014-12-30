package processing;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.DBConnection;

import vo.userVO;

public class loginprocess extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public loginprocess() {
    	
        super();
    }
    public int control=1;
	public static int step;
	public int count;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String username=request.getParameter("username");
	String password=request.getParameter("password");
	Properties prop = new Properties();
	prop.load(new FileInputStream("Config.properties"));
	step = Integer.parseInt(prop.getProperty("step"));
	DBConnection dbconnection = new DBConnection();
	Connection conn = dbconnection.getConnection();
	StringBuilder query = new StringBuilder();
	try{
	query.append("SELECT type from userinfo where username='");
	query.append(username);
	query.append("' and password='");
	query.append(password);
	query.append("'");
	ResultSet rs = conn.createStatement().executeQuery(query.toString());
	if(rs.next())
	{
		String type = rs.getString("type");
		userVO obj = new userVO();
		obj.setUsername(username);
		obj.setPassword(password);
		obj.setType(type);
		request.getSession().setAttribute("user",obj);
		request.getSession().setAttribute("control",control);
		request.getSession().setAttribute("step",step);
		if(type.equalsIgnoreCase("user"))
		{
		RequestDispatcher view = request.getRequestDispatcher("userpaging?control=1");
		view.forward(request,response);
		}
		else if(type.equalsIgnoreCase("admin"))
		{
		RequestDispatcher view = request.getRequestDispatcher("/adminview.jsp");
		view.forward(request,response);
		}
		}
	else
	{
		RequestDispatcher view = request.getRequestDispatcher("/loginfail.jsp");
		view.forward(request,response);
	}
	}catch(Exception e){
		System.out.println("Exception Found in loginprocess servlet : " + e);
	}
	}
}