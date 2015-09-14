//The following variables are declared so that it is easier to switch domains (which is needed for our ajax calls to our rest service)
var local_url = "http://localhost:8080/AjaxController/AjaxController/";
var xerox_url = "http:///"//removed for Xerox privacy reasons;
	
//var replace_url = xerox_url; //Pick local_url or xerox_url 
var replace_url = local_url; //Pick local_url or xerox_url 
//Don't forget to change SUBMIT 2 button in here at the bottom

$(document).ready(function() {
	loadSolutionList(); //load the solution list on start up
	$('#loading-component-click').hide(); //We are just hiding the loading animations that are underneath (component, start year,)
	$('#loading-year-click').hide();
	//the loading animation for the solution is hidden in the function loadSolutionList()

	$('#solution_param').change(function() { //this will update the component_id
		update_comp();
	});

	$('#component_param').change(function() { //this will update the month and year date
		update_year();
	});

	$('#yearparam_start').change(function() { //this will update the month
		update_month();
	});

	$('#monthparam_start').change(function() { //Once month changes, it will check to see if all 4 options have been chosen and then call DisplayLoadingMessage to draw graph
		if (verifyAdSettings()) { //if all 4 options have been chosen
			DisplayLoadingMessage();
			return;
		}
	});
	
	$('#first_ul li').mouseenter(function() {//The following two similar functions is to highlight and un-highlight the links to Reports at the Top Right
		if(this.id == "first" || this.id == "second" || this.id == "third" || this.id == "fourth"){
				$(this).removeClass('list_start');
			    $(this).addClass('list_highlight');
		}
	});
	$('#first_ul li').mouseleave(function() {
		if(this.id == "first" || this.id == "second" || this.id == "third" || this.id == "fourth"){
			$(this).removeClass('list_highlight');
			$(this).addClass('list_start');
		}
	});

});//end of $(document).ready(function() {


function DisplayLoadingMessage() {//Displays a loading message when we're about to print the google chart
	$.blockUI({ //using the iPhoto blocker from blockUI
		css: {
			border: 'none',
			padding: '15px',
			backgroundColor: '#000',
			'-webkit-border-radius': '10px',
			'-moz-border-radius': '10px',
			opacity: .9,
			color: '#fff'
		}
	});
	GoogleChart();//Call a helper function to draw a chart
	setTimeout($.unblockUI, 300);//unblock 
	return;
}//end of DisplayLoadingMessage()


function GoogleChart() {
	var solution = $('#solution_param').val();//Grab Parameter Values
	var component = $('#component_param').val();
	var year = $('#yearparam_start').val();
	var month = $('#monthparam_start').val();

	var temp_url = replace_url;//Formulate URL
	temp_url += "Report1/";
	temp_url += solution + "/";
	temp_url += component + "/";
	temp_url += year + "/";
	temp_url += month;
	
	$.ajax({
		type: 'GET',
		dataType: 'json',
		url: temp_url,
		success: function(response) {
			drawchart(response); // calling helper function to draw the chart
		},
		error: function() {
			console.log("Error loading data! Please try again.");
		}
	});//end of AJAX call
}//end of GoogleChart()



function drawchart(dataValues) {
	// Callback that creates and populates a data table,  
	// instantiates the pie chart, passes in the data and  
	// draws it.  
	var data = new google.visualization.DataTable();

	data.addColumn('string', 'Date');//Add column names
	data.addColumn('number', 'Request_Count');

	$.each(dataValues, function(k, v) {
		data.addRow([dataValues[k].DATE, parseInt(dataValues[k].REQUEST_COUNT, 10)]);//loop through json string and add date and request count
	});

	// Instantiate and draw our chart, passing in some options  
	var chart = new google.visualization.ColumnChart(document.getElementById('chartdiv'));//make a column chart

	var sol = $('#solution_param').val();//get the value of solution and component
	var com = $('#component_param').val();

	var options = {
		title: 'Request Count by Date (solution: ' + sol + ', component: ' + com + ')',
		titlePosition: 'in',//title is on the graph
		chartArea: {
			left: 115,
			top: 10,
			width: '80%',
			height: '80%'
		},
		hAxis: {
			title: 'Date'
		},
		vAxis: {
			textPosition: 'in',
			title: 'Request Count',
			minValue: 0
		},
		animation: {
			easing: 'in',
			startup: true
		},
		legend: {
			position: 'bottom'
		}
	};

	var formatter = new google.visualization.NumberFormat({//This adds commas to the formatter and make it so there are no digits after the DECIMAL POINT
		groupingSymbol: ',',
		fractionDigits: 0
	});
	formatter.format(data, 1);//the column that you want to format

	chart.draw(data, options);
}//end of drawchart


function update_comp() { //changed solution id, to update component param
	var solution = $('#solution_param').val();

	var $cp = $('#component_param');
	$cp.empty(); //clear out the select options
	$('#monthparam_start').empty();
	$('#yearparam_start').empty();

	var temp_url = replace_url;//Formulate URL
	temp_url += "GenerateComponentList";
	temp_url += "/";
	temp_url += solution;

	$.ajax({
		url: temp_url,
		beforeSend: function() {
			$('#loading-solution-click').show();
		},
		type: 'GET',
		dataType: 'json',
		success: function(order) {
			if (jQuery.isEmptyObject(order)) { //returns true if it is empty, when there are no unique component_ids given the solution_id
				$cp.append($('<option>', {value:""}).text("No options"));
				$('#yearparam_start').empty();//empty out selection fields
				$('#monthparam_start').empty();
				$('#yearparam_start').append($('<option>', {value:""}).text("No options"));//Add the No options field
				$('#monthparam_start').append($('<option>', {value:""}).text("No options"));
			} else {
				$cp.append($('<option>', {value:""}).text("Please select an option"));//If the object is not empty, then add "Please select an option"
				$('#yearparam_start').append($('<option>', {value:""}).text("Please select an option"));//as well as for these
				$('#monthparam_start').append($('<option>', {value:""}).text("Please select an option"));
				$.each(order, function(key, value) {//Loop through, append options 
					$cp.append($('<option>', {
						value: value
					}).text(value));
				});
			} //end of else
		}, //end of success
		complete: function() {
			$('#loading-solution-click').hide();
		}
	});//end of ajax call
	return false;
}//end of update_comp()

function update_year() { //this is helper function for when component param changes, update months and year

	var solution1 = $('#solution_param').val();
	var component1 = $('#component_param').val();
	var $start_year = $('#yearparam_start');

	var temp_url = replace_url;
	temp_url += "GenerateYearList";
	temp_url += "/";
	temp_url += solution1;
	temp_url += "/";
	temp_url += component1;

	$start_year.empty(); //clear out the year options
	$('#monthparam_start').empty(); //clear out the month options

	$.ajax({
		url: temp_url,
		beforeSend: function() {
			$('#loading-component-click').show(); //used to show the loading icon
		},
		type: 'GET',
		dataType: 'json',

		success: function(order) {
			if (jQuery.isEmptyObject(order)) { //checks to see whether there are valid years
				$('#yearparam_start').append($('<option>', {value:""}).text("No options"));//If they are not valid, add this to the following options
				$('#monthparam_start').append($('<option>', {value:""}).text("No options"));	
			} else { //there are valid years!!
				$start_year.append($('<option>', {value:""}).text("Please select an option"));
				$('#monthparam_start').append($('<option>', {value:""}).text("Please select an option"));
				$.each(order, function(key, value) {//each loop and append years
					$start_year.append($('<option>', {
						value: value
					}).text(value));
				});
			} //end of else
		}, //end of success
		complete: function() {
			$('#loading-component-click').hide(); //hide the loading element
		}
	});//end of AJAX call
	return false;
}//end of update_year()


//update month, similar to the one above
function update_month() { //this is helper function for when component param changes, update months and year

	var solution2 = $('#solution_param').val();//Grab Parameters
	var component2 = $('#component_param').val();
	var start_year2 = $('#yearparam_start').val();

	var temp_url = replace_url;//Formulate URL
	temp_url += "GenerateMonthList";
	temp_url += "/" + solution2;
	temp_url += "/" + component2;
	temp_url += "/" + start_year2;
	

	var $month = $('#monthparam_start');
	$month.empty(); //clear out the year options

	$.ajax({
		url: temp_url,
		beforeSend: function() {
			$('#loading-year-click').show();
		},
		type: 'GET',
		dataType: 'json',

		success: function(order) {
			$month.append($('<option>', {value:""}).text("Please select an option"));	
			$.each(order, function(key, value) {
				$month.append($('<option>', {
					value: value
				}).text(value));
			});
		},
		complete: function() {
			$('#loading-year-click').hide();
		}
	});//end of AJAX call
	return false;
}//end of update_month()


function verifyAdSettings() { //checks to see whether or not the selection fields are NULL
	if ($('#solution_param').val() != null && $('#component_param').val() != null && $('#monthparam_start').val() != null && $('#yearparam_start').val() != null

		&& $('#solution_param').val() != "" && $('#component_param').val() != "" && $('#monthparam_start').val() != "" && $('#yearparam_start').val() != ""
	) {
		return true;//return true if the options have all been selected
	}
	return false;//return false if the options have not been selected
}//end of verifyAdSettings()


function loadSolutionList(){//this function is only called once, during the loading of the page
	var temp_url = replace_url + "GenerateSolutionList";//Formulate URL
	var $sp = $('#solution_param');

	$.ajax({
		beforeSend: function() {
			$('#loading-solution-click').show();//before making the ajax call, show the loading solution
		},
		url: temp_url,
		type: 'GET',
		dataType: 'json',
		success: function(order) {
			$sp.append($('<option>', {value:""}).text("Please select an option"));
			$('#component_param').append($('<option>', {value:""}).text("Please select an option"));
			$.each(order, function(key, value) {//each loop, will add solution_ids to "solution_param" div
				$sp.append($('<option>', {
					value: value
				}).text(value));
			});
		},
		complete: function() {
			$('#loading-solution-click').hide();//After the call, hide the loading solution
		}
	});//end of ajax call
}//end of loadSolutionList()