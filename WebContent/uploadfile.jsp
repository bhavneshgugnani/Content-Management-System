<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<SCRIPT type="text/javascript" language="javascript">
var add;
var temp;
function addendum(){
	temp = document.createElement("input");
	temp.setAttribute("type","file");
	temp.setAttribute("name","filename");
	temp.setAttribute("id","submitfile");
	document.getElementById("addendum").appendChild(temp);
	temp = document.createElement("br");
	document.getElementById("addendum").appendChild(temp);
}
</SCRIPT>
<title>Insert title here</title>
</head>
<body>
<form method="post" action="uploadingprocess" enctype="multipart/form-data">
UPLOAD FILE(s)
<br>
<div id="addendum" name="addendum">
<input type="file" name="filename" id="submitfile"></td>
<br>
</div>
<a href="javascript:addendum()">Attach More Files</a>
<br>
<br>
<input type="submit" id="submit" name="submit" value="submit">
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<a href="logoutaction">LOGOUT</a>
</form>
</body>
</html>