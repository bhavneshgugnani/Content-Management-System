package processing;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.imageVO;
import vo.userVO;

import classes.DBConnection;
import classes.querymanager;

public class userpaging extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public userpaging() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	int control = Integer.parseInt(request.getParameter("control").toString());
	int step = Integer.parseInt(request.getSession().getAttribute("step").toString());
	querymanager temp1 = new querymanager();
	int count = temp1.count();
	if((control)<=0)
	{
		control=1;
	}
	if((control*step)>=count)
	{
		control=count/step;
	}
	request.getSession().setAttribute("control",control);
	int j=1;
	imageVO temp = new imageVO();
	StringBuilder query = new StringBuilder();
	query.append("SELECT * FROM imageinfo where moderated='yes'");		
	ArrayList<imageVO> data = new ArrayList<imageVO>();
	DBConnection dbconnection = new DBConnection();
	Connection conn = (Connection) dbconnection.getConnection();
	try{
		ResultSet rs = conn.createStatement().executeQuery(query.toString());
		while(rs.next()){
			if(j>=((step*(control-1))+1) && j<=(step*control)){
				temp = new imageVO();
				temp.setFilename(rs.getString("filename"));
				temp.setPath(rs.getString("path"));
				temp.setUpdatedby(rs.getString("updated_by"));
				temp.setModerated(rs.getString("moderated"));
				data.add(temp);
			}
			j++;
		}
	request.getSession().setAttribute("data",data);
	RequestDispatcher view = request.getRequestDispatcher("/userview.jsp");
	view.forward(request,response);
	}catch(Exception e)
	{
		System.out.println("Exception : " + e);
	}
	}
}