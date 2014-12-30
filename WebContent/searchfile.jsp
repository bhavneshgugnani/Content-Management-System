<%@ page import="java.util.Properties,java.io.FileInputStream,java.io.FileOutputStream,java.sql.Connection" language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<SCRIPT type="text/javascript" language="javascript">
var responsetext;
var name;
var databasematches;
var temp;
var i;
var tempstr;
var str;
var prop;
var step;

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

function ajax(){
	url = "http://localhost:8080/cmsnew/servercall";
	request.open("post",url,false);
	request.send(null);
	request.onReadyStateChange = returndata();
}

function returndata(){
	if(request.readyState==4){
		if(request.status==200){
			responsetext = request.responseText;	
		}
	}
}

function addendum(databasematches){
	for(i=0;i<databasematches.length;i++){
	tempstr = databasematches[i].split("`");
	filename = tempstr[0];
	path = tempstr[1];
	tempstr = filename;
	if(filename.match(".")){
		index = filename.indexOf(".");
		tempstr = filename.substr(0,index);
	}	
	temp = document.createTextNode(tempstr);
	document.getElementById("add").appendChild(temp);
	temp = document.createElement("input");
	temp.setAttribute("type","button");
	temp.setAttribute("value","Download");
	str = "window.location='http://localhost:8080/cmsnew/downloadimage?filename=" + filename + "&path=" + path + "'";
	temp.setAttribute("onclick",str);
	document.getElementById("add").appendChild(temp);
	temp = document.createElement("br");
	document.getElementById("add").appendChild(temp);
	}
}

function search(){
	name = document.getElementById("name").value;
	databasematches = new Array();
	document.getElementById("add").innerHTML="";
	if((name.trim())!="")
		{
		data = responsetext.split("|");
		for(i=0;i<((data.length)-1);i++){
			data1 = data[i].split("`");
			tempstr = data1[0].toLowerCase();
			if(tempstr.match(".")){
				index = tempstr.indexOf(".");
				tempstr = tempstr.substr(0,index);
			}
			if(tempstr.match(name)){
				databasematches.push(data[i]);
			}	
		}
	}
	addendum(databasematches);
}

function stepchange(){
	step = document.getElementById("step").value;
	document.getElementById(step).setAttribute("selected","selected");
	url = "http://localhost:8080/cmsnew/setconfigproperties?step=" + step;
	request.open("post",url,true);
	request.send(null);
	}

</SCRIPT>
<title>Search File</title>
</head>
<body>
<a href="redirector">HOME</a>
<br>
<a href="logoutaction">LOGOUT</a>
<br>
<br>
<table width="800px" align="center">
<tr><td align="left">
ENTER FILE NAME : <input type="text" id="name" name="name" onclick="ajax()" onkeyup="search()">
</td>
<td align="right">
Results per page : <select id="step" name="step" onchange="stepchange()">
<option id="2" value="2">2</option>
<option id="5" value="5">5</option>
<option id="10" value="10">10</option>
<option id="20" value="20">20</option>
<option id="50" value="50">50</option>
<option id="100" value="100">100</option>
</select>
</td>
</tr>
</table>
<br>
<br>
<br>
<ol id="add" name="add">
<table>
<tr id="filename" value="FILENAME" align="left"></tr>
<tr id="downloadlink" value="DOWNLOAD LINK" align="center"></tr>
</table>
</ol>
<br>
<br>
<br>
<div id="page" name="page">
</div>
<br>
<br>
</body>
</html>