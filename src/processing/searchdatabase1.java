package processing;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.userVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class searchdatabase1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public searchdatabase1() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarr = new JSONArray();
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
					jsonobj.put("filename",rs.getString("filename"));
					jsonobj.put("path",rs.getString("path"));
					jsonobj.put("id",i);
					jsonarr.add(jsonobj);
					}
					j++;
				}
			}
			request.getSession().setAttribute("step",step);
			request.getSession().setAttribute("control",control);
			response.setContentType("application/json");
			response.setHeader("Cache-Control","no-cache");
			PrintWriter out = response.getWriter();
			out.print(jsonarr);
			out.close();
			rs.close();
			conn.close();
			System.out.println("matches : " + i);
		}catch(Exception e){
			System.out.println("Exception in search2 : " + e);
		}	
	}
}