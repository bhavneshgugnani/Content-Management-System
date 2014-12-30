<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<SCRIPT type="text/javascript" language="javascript">
var name;
var str;
var size;
var url;
var temp;
var step;
var responsetext;
var i;
var j;
var data;
var datatemp;
var tempstr;
var step;
var control;
var count;
var last;

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

function search(step,control){
	name = document.getElementById("name").value;
	document.getElementById("add").innerHTML="";
	document.getElementById("page").innerHTML="";
	if(name!=""){
	url = "http://localhost:8080/cmsnew/searchdatabase?name=" + name + "&step=" + step + "&control=" + control; 
	request.open("post",url,false);
	request.send(null);
	request.onReadyStateChange = receivedata(step,control);
}
}

function receivedata(step,control){
	if(request.readyState==4){
		if(request.status==200){
			responsetext = request.responseText;
			data = responsetext.split("|");
			display(data,step,control);
		}
	}
}

function display(data,step,control){
	count = data.length;
	j=1;
	for(i=0;i<(count-1);i++){		
			datatemp = data[i].split("`");
			tempstr = datatemp[0];
			if(tempstr.match(".")){
				index = tempstr.indexOf(".");
				tempstr = tempstr.substr(0,index);
			}
			temp = document.createTextNode(tempstr);
			document.getElementById("add").appendChild(temp);
			temp = document.createElement("input");
			temp.setAttribute("type","button");
			temp.setAttribute("value","Download");
			str = "window.location='http://localhost:8080/cmsnew/downloadimage?filename=" + datatemp[0] + "&path=" + datatemp[1] + "'";
			temp.setAttribute("onclick",str);
			document.getElementById("add").appendChild(temp);
			temp = document.createElement("br");
			document.getElementById("add").appendChild(temp)
	}
	temp = document.createElement("input");
	temp.setAttribute("type","button");
	temp.setAttribute("value","Prev");
	last = (count/step);
	if(last%1!=0)
		{
		last = last - (last%1) + 1;
		}
	i = control;
	i = i-1;
	if(i<1){
		i=1;
		}
	if(i>(last-1))
		{
		i = last - 1;
		if(last==1)
			{
			i = 1;
			}
		}
	str = "prevrefresh(" + step + "," + i + ")";
	temp.setAttribute("onclick",str);
	document.getElementById("page").appendChild(temp);
	temp = document.createTextNode(control);
	document.getElementById("page").appendChild(temp);
	temp = document.createElement("input");
	temp.setAttribute("type","button");
	temp.setAttribute("value","Next");
	i = control;
	i=i+1;
	if(i>last)
		{
		i = last;
		}
	if(i<2){
		i = 2;
		if(last==1){
			i = 1;
		}
	}
	str = "nextrefresh(" + step + "," + i + ")";
	temp.setAttribute("onclick",str);
	document.getElementById("page").appendChild(temp);
}

function nextrefresh(step,control){
	name = document.getElementById("name").value;
	document.getElementById("add").innerHTML="";
	document.getElementById("page").innerHTML="";
	if(name!=""){
	url = "http://localhost:8080/cmsnew/searchdatabase?name=" + name + "&step=" + step + "&control=" + control; 
	request.open("post",url,false);
	request.send(null);
	request.onReadyStateChange = receiverefresheddata(step,control);
}
}

function prevrefresh(step,control){
	name = document.getElementById("name").value;
	document.getElementById("add").innerHTML="";
	document.getElementById("page").innerHTML="";
	if(name!=""){
	url = "http://localhost:8080/cmsnew/searchdatabase?name=" + name + "&step=" + step + "&control=" + control; 
	request.open("post",url,false);
	request.send(null);
	request.onReadyStateChange = receiverefresheddata(step,control);
}
}

function receiverefresheddata(step,control){
	if(request.readyState==4){
		if(request.status==200){
			responsetext = request.responseText;
			data = responsetext.split("|");
			display(data,step,control);
		}
	}
}

function stepchange(){
	step = document.getElementById("step").value;
	//document.getElementById(step).setAttribute("selected","selected");
	url = "http://localhost:8080/cmsnew/setconfigproperties?step=" + step;
	request.open("post",url,true);
	request.send(null);
	//window.location.reload();
	control = 1;
	search(step,control);
}

</SCRIPT>
<title>Search File</title>
</head>
<body>
<div><h1>SEARCH FILE</h1></div>
<%int control = Integer.parseInt(request.getSession().getAttribute("control").toString());
int step = Integer.parseInt(request.getSession().getAttribute("step").toString());%>
<a href="redirector">HOME</a>
<br>
<a href="logoutaction">LOGOUT</a>
<br>

<div align="right">
Results per page : <select id="step" name="step" onchange="stepchange()">
<option id="" value=""></option>
<option id="2" value="2">2</option>
<option id="5" value="5">5</option>
<option id="10" value="10">10</option>
<option id="20" value="20">20</option>
<option id="50" value="50">50</option>
<option id="100" value="100">100</option>
</select>
</div>
<br>
Enter Filename or part of filename : <input type="text" id="name" name="name" onkeyup="search(<%=step%>,<%=control%>)">
<br>
<br>
<div id="add" name="add">
</div>
<br>
<br>
<div id="page" name="page">
</div>
<br>
</body>
</html>