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
import classes.DBConnection;
import classes.querymanager;
public class adminpaging extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public adminpaging() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String moderation = request.getParameter("moderation");
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
		int j=1;
		imageVO temp = new imageVO();
		StringBuilder query = new StringBuilder();
		if(moderation.equalsIgnoreCase("yes")){
		query.append("SELECT * FROM imageinfo where moderated='no'");
		}
		else if(moderation.equalsIgnoreCase("no"))
		{
		query.append("SELECT * FROM imageinfo");
		}
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
		request.getSession().setAttribute("control",control);
		if(moderation.equalsIgnoreCase("yes")){
			RequestDispatcher view = request.getRequestDispatcher("/adminviewunmoderatedimages.jsp");	
			view.forward(request,response);
		}
		else if(moderation.equalsIgnoreCase("no"))
		{
			RequestDispatcher view = request.getRequestDispatcher("/adminviewallimages.jsp");
			view.forward(request,response);
		}
		
		}catch(Exception e)
		{
			System.out.println("Exception : " + e);
		}

	}

}
