package classes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.imageVO;

public class querymanager {
int count;

public ArrayList<imageVO> unmoderatedimages() throws FileNotFoundException, IOException{
		ArrayList<imageVO> data = new ArrayList<imageVO>();
		imageVO image = new imageVO();
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM imageinfo where moderated='no'");
		DBConnection dbconnection = new DBConnection();
		Connection conn = (Connection) dbconnection.getConnection();
		try{
			ResultSet rs = conn.createStatement().executeQuery(query.toString());
			while(rs.next()){
				image = new imageVO();
				image.setFilename(rs.getString("filename"));
				image.setPath(rs.getString("path"));
				image.setUpdatedby(rs.getString("updated_by"));
				image.setModerated(rs.getString("moderated"));
				data.add(image);
			}		
		}catch(Exception e)
		{
			System.out.println("Exception found in querymanager : " + e);
		}
		return(data);
	}
	
public ArrayList<String> getlist() throws ClassNotFoundException, SQLException
	{
		ArrayList<String> data = new ArrayList<String>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT * from imageinfo");
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3307/fileupload", "root", "redhat");
		try{
			ResultSet rs=conn.createStatement().executeQuery(query.toString());
			String filename=null;
			String path=null;
			String updatedby=null;
			while(rs.next())
			{
				filename=rs.getString("filename");
				path=rs.getString("path");
				updatedby=rs.getString("updated_by");
				data.add(filename);
				data.add(path);
				data.add(updatedby);
			}
			conn.close();
		}catch(Exception e)
		{
			System.out.println("Exception  : " + e);
		}	
		return (data);
	}

public int count(){
	try{
	DBConnection dbconnection = new DBConnection();
	Connection conn = dbconnection.getConnection();
	StringBuilder query = new StringBuilder();
	query.append("SELECT COUNT(*) AS count FROM imageinfo");
	ResultSet rs = conn.createStatement().executeQuery(query.toString());
	while(rs.next())
	{
		count=Integer.parseInt(rs.getString("count").toString());
	}
	}catch(Exception e){
		System.out.println("Exception in count method of querymanager.Exception : " + e);
	}
	return(count);
}

public ArrayList<imageVO> viewmoderatedimages(int step) throws FileNotFoundException, IOException{
		ArrayList<imageVO> data=new ArrayList<imageVO>();
		imageVO image;// = new imageVO();
		StringBuilder query = new StringBuilder();
		query.append("SELECT * from imageinfo where moderated='yes'");
		DBConnection dbconnection = new DBConnection();
		Connection conn = (Connection) dbconnection.getConnection();
		try{
			ResultSet rs = conn.createStatement().executeQuery(query.toString());
			count = rs.getMetaData().getColumnCount();
			int i=0;
			while(rs.next() && i<step)
			{
				image = new imageVO();
				image.setFilename(rs.getString("filename"));
				image.setPath(rs.getString("path"));
				image.setUpdatedby(rs.getString("updated_by"));
				image.setModerated(rs.getString("moderated"));
				data.add(image);
				i++;
			}
		}catch(Exception e){
			System.out.println("Exception found in querymanager : " + e);
		}	
		return (data);
	}

public ArrayList<imageVO> viewallimages() throws FileNotFoundException, IOException {
		ArrayList<imageVO> data=new ArrayList<imageVO>();
		imageVO image;
		StringBuilder query = new StringBuilder();
		query.append("SELECT* from imageinfo");
		DBConnection dbconnection = new DBConnection();
		Connection conn = (Connection) dbconnection.getConnection();
		try{
			ResultSet rs=conn.createStatement().executeQuery(query.toString());
			while(rs.next())
			{
				image = new imageVO();
				image.setFilename(rs.getString("filename"));
				image.setPath(rs.getString("path"));
				image.setUpdatedby("updated_by");
				image.setModerated("moderated");
				data.add(image);
			}	
		}catch(Exception e)
		{
			System.out.println("Exception found in querymanager : " + e);
		}
		return (data);
	}
}