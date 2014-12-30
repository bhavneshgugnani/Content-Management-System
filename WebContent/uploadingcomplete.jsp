<%@ page import="classes.querymanager,java.util.ArrayList" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>UPLOAD SUCCESSFULL</h2>
<br>
<%querymanager temp = new querymanager();
ArrayList<String> data = new ArrayList<String>();
data=temp.getlist();
String filename=null;
String path=null;
String updatedby=null;
String temp1=null;
for(int i=0;i<data.size();i=i+3)
{
filename=data.get(i);
path=data.get(i+1);
updatedby=data.get(i+2);
temp1=path+filename;
%>
<tr>
<td>
<%=filename%>
</td>
<td>
<a href=<%=temp1%> onclick="func(href)">VIEW FILE</a>
</td></tr>
<br>
<%}%>
<br>
<br>
<br>
<br>
<br>
<a href="uploadfile.jsp">UPLOAD FILE</a>
</body>
</html>