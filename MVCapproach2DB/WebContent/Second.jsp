<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<link rel="stylesheet" type="text/css" href="NewFile.css">
	
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>

		<script src="jquery-1.11.3.js"></script>
		<script src="second_javascript.js"></script>

		<script type="text/javascript">  
		   // Load the Visualization API and the piechart package.  	
			google.load('visualization', '1', { packages: ['corechart'] });//this is for the bar chart	
      // Global variable to hold data  
	      
     // google.load('visualization', '1.1', {packages:['table']});//this is for the table
      //google.setOnLoadCallback(drawTable);//this is supposed to be for the table, but didn't use
 	     </script>
 	     <script src="jquery.blockUI.js" ></script>

	      <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	      <title>Second Report!</title>
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
  					<li id="second"><a href="http://usa0300lv312.apps.mc.xerox.com:8080/MVCapproach2DB/Second.jsp" class="list_start" >Report 2 </a></li>
  					<li id="third"><a href="http://usa0300lv312.apps.mc.xerox.com:8080/MVCapproach2DB/Third.jsp" class="list_start" >Report 3 </a></li>
  					<li id="fourth"><a href="http://usa0300lv312.apps.mc.xerox.com:8080/MVCapproach2DB/Fourth.jsp" class="list_start" >Report 4 </a></li>
  					<!-- <li><a href="#">Portfolio </a></li> -->
  					<!-- <li><a href="#">Contact</a></li> -->
  				</ul>
  			</div>
  		</div>
  	</div>

  	<div class="container">

  		<div class="content">
  			<BR></BR>

  		<h1>Second Report!</h1>
  		<p> </p> Solution <span><strong>:</strong></span>
  		<select name="solution_param" id="solution_param" style="max-width: 90%">

		</select> 
		<div id="loading-solution-click">
			<img src="loading.gif" />
		</div>
	
		<p></p>
	
		Start Year<span><strong>:</strong></span>


		<select name="yearparam_start" id="yearparam_start" style="max-width: 90%">

		</select>

		<div id="loading-start_year-click">
			<img src="loading.gif" />
		</div>

		<p></p>
		Start Month<span><strong>:</strong></span>
		<select name="monthparam_start" id="monthparam_start" style="max-width: 90%">
		
		</select>


		<div id="loading-start_month-click">
			<img src="loading.gif" />
		</div>	

		<p></p> End Year<span><strong>:</strong></span>
		<select name="yearparam_end" id="yearparam_end" style="max-width: 90%">

		</select> 


		<div id="loading-end_year-click">
			<img src="loading.gif" />
		</div>


		<p></p>
		End Month<span><strong>:</strong></span>

		<select name="monthparam_end" id="monthparam_end" style="max-width: 90%">

		</select>
		
		<div id="loading-end_month-click">
			<img src="loading.gif" />
		</div>	

		<p></p>
		<p> </p>

		<!-- <input type="submit" name="Submit1" value="Report1" id="Submit1" /> -->

		<p></p>


		<div id="chartdiv" resize:both style="width: 1200px; height: 600px;"></div>
		</div>
  	</div>

  	<p></p>

	</body>
</html>