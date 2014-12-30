package processing;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.userVO;
public class searchdatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public searchdatabase() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int control = Integer.parseInt(request.getParameter("control").toString());
		int step = Integer.parseInt(request.getParameter("step").toString());
		String name = request.getParameter("name");
		name = name.toLowerCase();
		String returnquery = new String();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/fileupload","root","redhat");
			int i=0;
			int j=1;
			int index;
			userVO user = (userVO) request.getSession().getAttribute("user");
			String type = user.getType();
			StringBuilder query = new StringBuilder();
			if(type.equals("admin")){
				query.append("SELECT filename,path FROM imageinfo");
			}
			else if(type.equals("user")){
				query.append("SELECT filename,path FROM imageinfo where moderated='yes'");
			}
			System.out.println("query -------->" + query);
			ResultSet rs = conn.createStatement().executeQuery(query.toString());
			while(rs.next())
			{
				String filename = rs.getString("filename");
				if(filename.contains(".")){
				index = filename.lastIndexOf(".");
				filename = filename.substring(0,index);}
				filename = filename.toLowerCase();
					if(filename.startsWith(name))
					{	
					if(j>=((step*(control-1))+1) && j<=(step*control)){
					i++;
					System.out.println("match " + i + " : " + filename);
					returnquery+= rs.getString("filename") + "`" + rs.getString("path") + "|";
					}
					j++;
				}
			}
			request.getSession().setAttribute("step",step);
			request.getSession().setAttribute("control",control);
			System.out.println("returnquery : " + returnquery);
			response.setContentType("text/plain");
			response.setHeader("Cache-Control","no-cache");
			PrintWriter out = response.getWriter();
			out.write(returnquery);
			out.close();
			rs.close();
			conn.close();
			System.out.println("matches : " + i);
		}catch(Exception e){
			System.out.println("Exception in search2 : " + e);
		}
	}
}