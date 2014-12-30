package processing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.tomcat.util.http.fileupload.FileItem;

import classes.DBConnection;

import vo.userVO;


public class uploadingprocess extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	String filename;
 	public uploadingprocess() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		userVO user = (userVO)request.getSession().getAttribute("user");
		String username = user.getUsername();
		String type = user.getType();
		String path="c://upload//";

		try{
			DiskFileItemFactory factory1 = new DiskFileItemFactory();
			factory1.setSizeThreshold(10*1024);
			ServletFileUpload upload1 = new ServletFileUpload(factory1);
			List item = upload1.parseRequest(request);
			Iterator iter = item.iterator();
			HashMap<String,String> map = new HashMap<String,String>();
			while(iter.hasNext())
			{
				FileItem item1 = (FileItem) iter.next();
				if(item1.isFormField())
				{
					String name = item1.getFieldName();
					String value = item1.getString();
					map.put(name,value);
				}
				else
					if(!item1.isFormField())
					{
						InputStream uploadstream = item1.getInputStream();
						filename=item1.getName();
						int pos = filename.lastIndexOf("\\");
						filename = filename.substring(pos + 1);
						File file1 = new File(path + filename);
						item1.write(file1);
						uploadstream.close();		
					}
			}
				StringBuilder query = new StringBuilder();
				query.append("INSERT into imageinfo VALUES('");
				query.append(filename);
				query.append("','");
				query.append(path);
				String directory = path + filename;
				request.getSession().setAttribute("directory", directory);
				query.append("','");
				query.append(username);
				if(type.equalsIgnoreCase("user"))
				{
				query.append("','no')");
				}
				else if(type.equalsIgnoreCase("admin"))
				{
				query.append("','yes')");
				}
				DBConnection dbconnection = new DBConnection();
				Connection conn = dbconnection.getConnection();
				int i = conn.createStatement().executeUpdate(query.toString());
				if (i > 0) {
					conn.close();
					RequestDispatcher view = request.getRequestDispatcher("/uploadsuccess.jsp");
					view.forward(request,response);
			}	
				} catch(Exception ex) {
				System.out.println("Exception found in uploading process . Exception : " + ex);
			}
	}
}