package processing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.imageVO;
import vo.userVO;
import java.net.FileNameMap;
import vo.imageVO;
import vo.imageVO;
public class downloadimage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public downloadimage() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request,response);	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter("path");
		String filename = request.getParameter("filename");
		filename = path + filename;
		File file = new File(filename);
	    int length = 0;
	    ServletOutputStream op = response.getOutputStream();
	    ServletContext context  = getServletConfig().getServletContext();
	    String mimetype = context.getMimeType( filename );
	    response.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
	    response.setContentLength( (int)file.length() );
	    response.setHeader( "Content-Disposition", "attachment; filename=\"" + filename + "\"" );        
	    byte[] buf = new byte[1024];
	    DataInputStream in = new DataInputStream(new FileInputStream(file));
	    while ((in != null) && ((length = in.read(buf)) != -1))
	    {
	        op.write(buf,0,length);
	    }
	    in.close();
	    op.flush();
	    op.close();
	}
}