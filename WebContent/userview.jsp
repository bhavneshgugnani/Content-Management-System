<%@ page import="vo.imageVO,classes.querymanager,java.util.ArrayList" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<SCRIPT type="text/javascript" language="javascript"> 
var step;
var url;
var path;
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
	request.open("get",url,false);
	request.send(null);
	window.location.reload();
}

function openwindow(path,filename){
	alert("in open window method.Path : " + path + " and Filename : " + filename);
	window.open("file:///" + path + filename,"Image Window");
}

</SCRIPT>
<title>User View</title>
</head>
<body>
<h2>IMAGES</h2>
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
System.out.println("in user view");
ArrayList<imageVO> data = new ArrayList<imageVO>();
imageVO image;
int step = Integer.parseInt(request.getSession().getAttribute("step").toString());
int control = Integer.parseInt(request.getSession().getAttribute("control").toString());
System.out.println("control : " + control);
data = (ArrayList<imageVO>)request.getSession().getAttribute("data");
for(int i=0;i<data.size();i++)
{
	image = new imageVO();
	image=data.get(i);
%>
<tr>
<td>
<%=image.getFilename()%>
</td>
<td>
<a href="file:///<%=image.getPath() + image.getFilename()%>">VIEW IMAGE</a>
<a href="javascript:openwindow('<%=image.getPath()%>','<%=image.getFilename()%>')">new window</a>
</td>
<td>
<a href="downloadimage?filename=<%=image.getFilename()%>&path=<%=image.getPath()%>">Download Image</a>
</td>
</tr>
<br>
<br>
<%}%>
<br>
<tr>
<td><a href="userpaging?control=<%=control-1%>">Prev</a></td>
|<td><b><%=control%></b></td>|
<td><a href="userpaging?control=<%=control+1%>">Next</a></td>
</tr>
<br>
<br>
<a href="searchfile2.jsp">SEARCH FILE</a>
<br>
<a href="uploadfile.jsp">UPLOAD FILE</a>
<br>
<a href="logoutaction">LOGOUT</a>
<br>
</body>
</html>