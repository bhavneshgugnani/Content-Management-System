package processing;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.userVO;
public class servercall extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public servercall() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String returnquery = new String();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/fileupload","root","redhat");
			int i=0;
			int index;
			StringBuilder query = new StringBuilder();
			userVO user = (userVO) request.getSession().getAttribute("user");
			if(user.getType().equalsIgnoreCase("user"))
			{
				query.append("SELECT filename,path FROM imageinfo WHERE moderated='yes'");
			}
			else if(user.getType().equalsIgnoreCase("admin"))
			{
				String moderation = request.getParameter("moderation");
				query.append("SELECT filename,path FROM imageinfo");
			}
			ResultSet rs = conn.createStatement().executeQuery(query.toString());
			while(rs.next())
			{
				returnquery+= rs.getString("filename") + "`" + rs.getString("path") + "|"; 
			}
			response.setContentType("text/plain");
			response.setHeader("Cache-Control","no-cache");
			PrintWriter out = response.getWriter();
			out.write(returnquery);
			out.close();
			rs.close();
			conn.close();
		}catch(Exception e){
			System.out.println("Exception in search2 : " + e);
		}
	}
}