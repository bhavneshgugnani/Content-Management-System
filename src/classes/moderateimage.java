package classes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class moderateimage {
moderateimage(String filename) throws FileNotFoundException, IOException{
	DBConnection dbconnection = new DBConnection();
	Connection conn = dbconnection.getConnection();
	StringBuilder query = new StringBuilder();
	query.append("UPDATE imageinfo set moderated='yes' where filename='");
	query.append(filename);
	query.append("'");
	try{
		int rs = conn.createStatement().executeUpdate(query.toString());
		if(rs==1)
		{
			System.out.println("Image moderated succesfully");
		}
		else
		{
			System.out.println("Moderation unsuccessful");
		}
	}catch(Exception e){
		System.out.println("Exception found in moderateimage : " + e);
	}
}
}
