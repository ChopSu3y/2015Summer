<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="NewFile.css">


		<!--Load the AJAX API-->
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>

		<script src="jquery-1.11.3.js" type="text/javascript"></script>
		<script src="first_javascript.js" type="text/javascript"></script>

		<script type="text/javascript">  
    		google.load('visualization', '1', { packages: ['corechart'] });//this is for the bar chart
    	</script>

    	<script src="jquery.blockUI.js" type="text/javascript"></script>

    	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    	
    	<title>First Report!</title>
  	</head>
  	<body>

  	<div class="header">
  		<div class="container">
  			<div class="logo">
  				<h1> XIM EIPC </h1>
  			</div>
 			<div class="nav">
  				<ul id="first_ul">
  					<!-- <li id="home">Home</li> -->
  					<li id="first"><a href="http://usa0300lv312.apps.mc.xerox.com:8080/MVCapproach2DB/First.jsp" class="list_start" >Report 1 </a></li>
  					<li id="second"><a href="http://usa0300lv312.apps.mc.xerox.com:8080/MVCapproach2DB/Second.jsp" class="list_start">Report 2 </a></li>
  					<li id="third"><a href="http://usa0300lv312.apps.mc.xerox.com:8080/MVCapproach2DB/Third.jsp" class="list_start" >Report 3 </a></li>
  					<li id="fourth"><a href="http://usa0300lv312.apps.mc.xerox.com:8080/MVCapproach2DB/Fourth.jsp" class="list_start" >Report 4 </a></li>
  					<!-- <li><a href="#">Portfolio </a></li> -->
  					<!-- <li><a href="#">Contact</a></li> -->
  				</ul>
  			</div>
  		</div>
  	</div>
<!-- http://usa0300lv312.apps.mc.xerox.com:8080/MVCapproach2DB/Second.jsp -->
  	<div class="container">

  		<div class="content">
  			<br></br>	

  			<h1>
  				First Report!
  			</h1>

  			Solution<span><strong>:</strong></span>
  			<select id="solution_param" name="solution_param" style="max-width: 90%">
  			</select>

  			<div id="loading-solution-click">
  				<img src="loading.gif" alt="loading.gif not found"/>
  			</div>

  			<p></p>
  			Component<span><strong>:</strong></span>
  			<select id="component_param" name="component_param" style="max-width: 90%">
  			</select>

  			<div id="loading-component-click">
  				<img src="loading.gif" alt="loading.gif not found"/>
  			</div>


  			<p></p>
  			Start Year<span><strong>:</strong></span>
  			<select id="yearparam_start" name="yearparam_start" style="max-width: 90%">
  				<option value="">Please select a year</option>
  			</select>

  			<div id="loading-year-click">
  				<img src="loading.gif" alt="loading.gif not found"/>
  			</div>

  			<p></p>
  			Start Month<span><strong>:</strong></span>
  			<select id="monthparam_start" name="monthparam_start" style="max-width: 90%">
  				<option value="">Please select a month</option>
  			</select>

  			<p></p>

  			<!-- <input type="submit" name="Submit2" value="Report2" id="Submit2" /> -->

			<div id="chartdiv" style="width: 1200px; height: 600px;"></div>

  		</div>
  	</div>

  		

  		<p></p>
  		

  	</body>
</html>