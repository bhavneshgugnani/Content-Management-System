<%@ page import ="vo.imageVO,classes.querymanager,java.util.ArrayList" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<SCRIPT >
var step;
var url;
var filename;

try {
	  request = new XMLHttpRequest();
	} catch (trymicrosoft) {
	  try {
	    request = new ActiveXObject("Msxml2.XMLHTTP");
	  } catch (othermicrosoft) {
	    try {
	      request = new ActiveXObject("Microsoft.XMLHTTP");
	    } catch (failed) {
	      request = false;
	    }  
	  }
	}

function stepchange(){
	step = document.getElementById("step").value;
	url = "http://localhost:8080/cmsnew/setconfigproperties?step=" + step;
	request.open("post",url,false);
	request.send(null);
	window.location.reload();
}

function callmoderateimage(filename)
{
	alert("callmoderateimage : " + filename);
//moderateimage temp=new moderateimage(filename);
}
</SCRIPT>
<body>
<form method="get" action="moderate">
<h1>UNMODERATED IMAGES</h1>
<div align="right">
Results per page : <select id="step" name="step" onchange="stepchange()">
<option value=""></option>
<option value="2">2</option>
<option value="5">5</option>
<option value="10">10</option>
<option value="20">20</option>
<option value="50">50</option>
<option value="100">100</option>
</select>
</div>
<br>
<% 
ArrayList<imageVO> data = new ArrayList<imageVO>();
imageVO image;
int step = Integer.parseInt(request.getSession().getAttribute("step").toString());
int control = Integer.parseInt(request.getSession().getAttribute("control").toString());
System.out.println("control : " + control);
data = (ArrayList<imageVO>)request.getSession().getAttribute("data");
for(int i=0;i<data.size();i++)
{
	image = new imageVO();
	image = data.get(i);
%>
<tr>
<td><%=image.getFilename()%></td>
<td><a href="<%=image.getPath() + image.getFilename()%>">VIEW IMAGE</a></td>
<td><input type="checkbox" id="<%=image.getFilename()%>" name="<%=image.getFilename()%>"></td>
<td><a href="downloadimage?filename=<%=image.getFilename()%>&path=<%=image.getPath()%>">Download Image</a></td>
</tr>
<br>
<br>
<%}
if(data.size()!=0){
%>
<br>
<br>
<input type="submit" id="submit" name="submit" value="submit">
<br>
<br>
<tr>
<td><a href="adminpaging?control=<%=control-1%>&moderation=yes">Prev</a></td>
|<td><b><%=control%></b></td>|
<td><a href="adminpaging?control=<%=control+1%>&moderation=yes">Next</a></td>
</tr>
<%}else{%>
<h2>NO IMAGES AVAILAIBLE FOR MODERATION</h2>
<%}%>
</form>
<br>
<br>
<br>
<a href="searchfile2.jsp">SEARCH FILE</a>
<br>
<a href="adminview.jsp">MAIN PAGE</a>
<br>
<a href="logoutaction">LOGOUT</a>
<br>
</body>
</html>