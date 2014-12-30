package processing;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.userVO;

import classes.DBConnection;
public class moderate extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public moderate() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Enumeration enumer = request.getParameterNames();
	StringBuilder query = new StringBuilder();
	String temp;
	try{
		while(enumer.hasMoreElements()){
		DBConnection dbconnection = new DBConnection();
		Connection conn = (Connection) dbconnection.getConnection();
		Statement stmt = conn.createStatement();
		query.append("UPDATE imageinfo SET moderated='yes' where filename='");
		temp = enumer.nextElement().toString();
		temp=URLEncoder.encode(temp,"UTF-8");
		query.append(temp);
		query.append("'");
		int i=stmt.executeUpdate(query.toString());
		stmt.close();
		conn.close();
		}
		RequestDispatcher view=null;
		userVO user = (userVO) request.getSession().getAttribute("user");
		if(user.getType().equalsIgnoreCase("user"))
	{
		view = request.getRequestDispatcher("/userview.jsp");
	}
	else
	if(user.getType().equalsIgnoreCase("admin"))
	{
		view = request.getRequestDispatcher("/adminview.jsp");
	}
	view.forward(request, response);
	}catch(Exception e)
	{
		e.printStackTrace();
		System.out.println("Exception in moderate : " + e);
	}
	}
}