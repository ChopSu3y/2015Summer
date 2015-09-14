//The following variables are declared so that it is easier to switch domains (which is needed for our ajax calls to our rest service)
var local_url = "http://localhost:8080/AjaxController/AjaxController/";
var xerox_url = "http://"; //Removed for Xerox Privacy Reasons

//var replace_url = xerox_url; //Pick local_url or xerox_url 
var replace_url = local_url;
//Don't forget to change SUBMIT 2 button in here at the bottom
$(document).ready(function() {
	$('#loading-start_year-click').hide(); //underneath component
	$('#loading-end_year-click').hide();
	
	$('#loading-start_month-click').hide();
	$('#loading-end_month-click').hide();
	$('#loading-component-click').hide();
	
	$('#loading-start_day-click').hide();
	$('#loading-end_day-click').hide();
	populateSolutionList(); //puts the options for the Solution Drop-Down list
	SubmitEnableOrDisable();//this is just a test function to see if disabling the submit button works or not
	
	$('#solution_param').change(function () {
		populateComponentList();
		SubmitEnableOrDisable();
	});
	
	$('#component_param').change( function() {
		update_year_and_clear_month( $('#yearparam_start'), $('#monthparam_start') , $('#dayparam_start'), $('#loading-start_year-click'));
		
		update_year_and_clear_month( $('#yearparam_end'), $('#monthparam_end') , $('#dayparam_end'), $('#loading-end_year-click'));
		SubmitEnableOrDisable();
	});
	
	$('#yearparam_start').change(function() { //this will update the month selection list when you change a year 
		update_month($('#monthparam_start'), $('#yearparam_start'), $('#dayparam_start'), $('#loading-start_month-click'));
		SubmitEnableOrDisable();
	});
	$('#yearparam_end').change(function() { //exactly the same as the above function
		update_month($('#monthparam_end'), $('#yearparam_end'), $('#dayparam_end'), $('#loading-end_month-click'));
		SubmitEnableOrDisable();
	});
	
	$('#monthparam_start').change(function() { //this will update the month selection list when you change a year 
		update_days( $('#dayparam_start'), $('#monthparam_start'), $('#yearparam_start'), $('#loading-start_month-click'));
		SubmitEnableOrDisable();
	});

	$('#monthparam_end').change(function() { //exactly the same as the above function
		update_days( $('#dayparam_end'), $('#monthparam_end'), $('#yearparam_end'), $('#loading-end_month-click'));
		SubmitEnableOrDisable();
	});

	
	$('#dayparam_end').click(function() {
		SubmitEnableOrDisable();
	});
	$('#dayparam_start').click(function() {
		SubmitEnableOrDisable();
	});
	
	
	$('#first_ul li').mouseenter(function() {//The following two similar functions is to highlight and un-highlight the links to Report 1, Report 2, and Report 3
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

	var chart = new google.visualization.ColumnChart(document.getElementById('chartdiv'));	
	$('#column').click(function() {
		
		if(CompareDate()){
			$.blockUI({ //using the iPhoto blocker from blockUI, we will load an overlay to display "Please wait..." when loading the chart 
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
		
		var solution = $('#solution_param').val();
		var component = $('#component_param').val();
		
		var start_day = $('#dayparam_start').val();//grabbing the values to make start date
		var start_month = $('#monthparam_start').val();
		var start_year = $('#yearparam_start').val();
		
		var start_date = start_day + "-"  + start_month + "-" + start_year;//format: day-month-year
		
		var end_day = $('#dayparam_end').val();//grabbing the values to make end date
		var end_month = $('#monthparam_end').val();
		var end_year = $('#yearparam_end').val();
		
		var end_date = end_day + "-"  + end_month + "-" + end_year;
		
		
		var temp_url = replace_url;//Formulate URL
		temp_url += "Report4/";
		temp_url+= solution + "/";
		temp_url+= component + "/";
		temp_url+= start_date + "/";
		temp_url+= end_date;
		console.log("Report 4 rest call is:" + temp_url);	
		
		chart = new google.visualization.ColumnChart(document.getElementById('chartdiv'));	
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: temp_url,
			success: function(response) {
				drawchart(response);
			},
			error: function() {
				console.log("Error loading data! Please try again.");
			},
			complete: function() {
				setTimeout($.unblockUI, 0);
			}
		});//end of AJAX Calls 
		}//end of if statement
		else{//the start date is after the end date
			
			$.blockUI({ //using auto-unblock from blockUI
				message: '<h1>Make sure start date is earlier than end date!</h1>', //displays a message and automatically disappears after 3 seconds
				timeout: 3000
			});
		}//end of else statement
});//end of $('#column').click(function()
	
	
$('#line').click(function() {
	if(CompareDate()){
		$.blockUI({ //using the iPhoto blocker from blockUI, we will load an overlay to display "Please wait..." when loading the chart 
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
	
		var solution = $('#solution_param').val();
		var component = $('#component_param').val();
		
		var start_day = $('#dayparam_start').val();//grabbing the values to make start date
		var start_month = $('#monthparam_start').val();
		var start_year = $('#yearparam_start').val();
		
		var start_date = start_day + "-"  + start_month + "-" + start_year;
		
		var end_day = $('#dayparam_end').val();//grabbing the values to make end date
		var end_month = $('#monthparam_end').val();
		var end_year = $('#yearparam_end').val();
		
		var end_date = end_day + "-"  + end_month + "-" + end_year;
		
		var temp_url = replace_url;
		temp_url += "Report4/";
		temp_url+= solution + "/";
		temp_url+= component + "/";
		temp_url+= start_date + "/";
		temp_url+= end_date;
		
		console.log("Report 4 rest call is:" + temp_url);
		
		chart = new google.visualization.LineChart(document.getElementById('chartdiv'));	
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: temp_url,
			success: function(response) {
				drawchart(response);
			},
			error: function() {
				console.log("Error loading data! Please try again.");
			},
			complete: function() {
				setTimeout($.unblockUI, 0); //this stops the overlay after .3 seconds
			}
		});//end of ajax call
			}//end of if statement
	else{//the start date is after the end date
		
		$.blockUI({ //using auto-unblock from blockUI
			message: '<h1>Make sure start date is earlier than end date!</h1>', //displays a message and automatically disappears after 3 seconds
			timeout: 3000
		});
		
	}//end of else staement
});//end of $('#line').click(function()
	
	
	
	function drawchart(dataValues) {
		// Callback that creates and populates a data table,  
		// instantiates the pie chart, passes in the data and  
		// draws it.  
		  var data = new google.visualization.DataTable();
		  

			var solution = $('#solution_param').val();
			var component = $('#component_param').val();
			
			var start_day = $('#dayparam_start').val();//grabbing the values to make start date
			var start_month = $('#monthparam_start').val();
			var start_year = $('#yearparam_start').val();
			
			var start_date = start_day + "-"  + start_month + "-" + start_year;
			
			var end_day = $('#dayparam_end').val();//grabbing the values to make end date
			var end_month = $('#monthparam_end').val();
			var end_year = $('#yearparam_end').val();
			
			var end_date = end_day + "-"  + end_month + "-" + end_year;

		data.addColumn('string', 'Date');
		data.addColumn('number', 'Error');
		data.addColumn('number', 'Requests');
		$.each(dataValues, function(k,v) {
			var original = dataValues[k].DATE;
			data.addRow([ original, parseInt(dataValues[k].ERROR_COUNT), parseInt(dataValues[k].REQUEST_COUNT)]);
		});
				
		// Instantiate and draw our chart, passing in some options  
		var options = {
				 colors: ['#dc3912', '#3366cc'],
				 vAxes: {0: {logScale: false, title: 'Error'},
			            1: {logScale: false, title: 'Total Request' }},
			            
			            series:{
			                0:{targetAxisIndex:0},
			                1:{targetAxisIndex:1}
			             },
			                   

				title: '(Sol:' + solution + '),' + ' (Comp:' + component + ')       Daily Errors & Requests       ('+ start_date + ' to ' + end_date + ')',
				titlePosition: 'in',
				pointSize: 10,
					chartArea: {
							left: 115,
							top: 10,
							width: '80%',
							height: '80%'
						},
					hAxis:{
						title: 'Date',
						gridlines:{
							color: '#333',
							count: 4
						}
					},
					
					vAxis:{
						title: 'Errors',
						gridlines:{
							color: '#333',
							count: 4
						}
					},
					
		          legend: { position: 'bottom' }
		};

		
		var formatter = new google.visualization.NumberFormat({//this will change the number formats to have commas and to have 0 digits after the decimal point
			groupingSymbol: ',',
			fractionDigits: 0
		});
		formatter.format(data, 1);//format both columns
		formatter.format(data, 2);
		chart.draw(data, options);
	}//end of drawchart()
	
	
});//end of Document Ready()


function populateSolutionList() {
	var $sp = $('#solution_param');

	var temp_url = replace_url + "GenerateSolutionList";

	$.ajax({
		beforeSend: function() {
			$('#loading-solution-click').show(); //show loading animation
		},
		url: temp_url,
		type: 'GET',
		dataType: 'json',
		success: function(order) {
			$sp.append($('<option>', {value:""}).text("Please select an option"));
			$('#component_param').append($('<option>', {value:""}).text("Please select an option"));
			$('#yearparam_start').append($('<option>', {value:""}).text("Please select an option"));
			$('#yearparam_end').append($('<option>', {value:""}).text("Please select an option"));
			
			$('#dayparam_start').append($('<option>', {value:""}).text("Please select an option"));
			$('#dayparam_end').append($('<option>', {value:""}).text("Please select an option"));
			
			$('#monthparam_start').append($('<option>', {value:""}).text("Please select an option"));
			$('#monthparam_end').append($('<option>', {value:""}).text("Please select an option"));
			
			
			$.each(order, function(key, value) { //loop through and populate the option with the text and value from the JSON array
				$sp.append($('<option>', {
					value: value
				}).text(value));
			});//end of each loop
		},//end of success:
		error: function() {
			console.log("There is an error in populateSolutionList()");
		},
		complete: function() {
			$('#loading-solution-click').hide(); //hide loading animation
		}
	}); //end of ajax call
} //end of populateSolutionList



function populateComponentList() {
	var $sp = $('#solution_param');
	var $cp = $('#component_param');
	
	var temp_url = replace_url + "GenerateCompList4/" + $sp.val();
	
	$cp.empty();//empty out the component list
	
	$('#yearparam_start').empty();
	$('#monthparam_start').empty();
	$('#dayparam_start').empty();
	
	$('#yearparam_end').empty();
	$('#monthparam_end').empty();
	$('#dayparam_end').empty();
	
	$.ajax({
		beforeSend: function() {
			$('#loading-component-click').show(); //show loading animation
		},
		url: temp_url,
		type: 'GET',
		dataType: 'json',
		success: function(order) {
			if (jQuery.isEmptyObject(order)) { //returns true if it is empty
				$cp.append($('<option>', {value:""}).text("No options"));
			
				$('#yearparam_start').append($('<option>', {value:""}).text("No options"));
				$('#monthparam_start').append($('<option>', {value:""}).text("No options"));
				
				$('#yearparam_end').append($('<option>', {value:""}).text("No options"));
				
				$('#monthparam_end').append($('<option>', {value:""}).text("No options"));
				
				$('#dayparam_end').append($('<option>', {value:""}).text("No options"));
				$('#dayparam_start').append($('<option>', {value:""}).text("No options"));
				
			} else {
				$('#component_param').append($('<option>', {value:""}).text("Please select an option"));
				
				$('#yearparam_start').append($('<option>', {value:""}).text("Please select an option"));
				$('#monthparam_start').append($('<option>', {value:""}).text("Please select an option"));
				$('#dayparam_start').append($('<option>', {value:""}).text("Please select an option"));
				
				$('#yearparam_end').append($('<option>', {value:""}).text("Please select an option"));
				$('#monthparam_end').append($('<option>', {value:""}).text("Please select an option"));
				$('#dayparam_end').append($('<option>', {value:""}).text("Please select an option"));
				
				$.each(order, function(key, value) {
					$cp.append($('<option>', {
						value: value
					}).text(value));
				});//end of each loop
			} //end of else
		}, //end of success:
		error: function() {
			$('#component_param').append($('<option>', {value:""}).text("Please select an option"));
			
			$('#yearparam_start').append($('<option>', {value:""}).text("Please select an option"));
			$('#monthparam_start').append($('<option>', {value:""}).text("Please select an option"));
			$('#dayparam_start').append($('<option>', {value:""}).text("Please select an option"));
			
			$('#yearparam_end').append($('<option>', {value:""}).text("Please select an option"));
			$('#monthparam_end').append($('<option>', {value:""}).text("Please select an option"));
			$('#dayparam_end').append($('<option>', {value:""}).text("Please select an option"));
		},
		complete: function() {
			$('#loading-component-click').hide(); //hide loading animation
		}
	}); //end of ajax call
}//end of populateComponentList


function update_year_and_clear_month(year, month, day, loading) { //this is helper function for when component param changes, update months and year
	var solution1 = $('#solution_param').val();
	var compo = $('#component_param').val();

	var temp_url = replace_url + "GenerateYearList4/";
	temp_url+=solution1 + "/";
	temp_url+=compo;

	year.empty(); //clear out the year options
	month.empty(); //clear out the month options
	day.empty();//clear out the day options

	$.ajax({
		url: temp_url,
		beforeSend: function() {
			loading.show(); //used to show the loading icon
		},
		type: 'GET',
		dataType: 'json',
		success: function(order) {
			if (jQuery.isEmptyObject(order)) { //checks to see whether there are valid years
				year.append($('<option>', {value:""}).text("No options"));
				month.append($('<option>', {value:""}).text("No options"));
				day.append($('<option>', {value:""}).text("No options"));
				
			} else { //there are valid years!!
				year.append($('<option>', {value:""}).text("Please select an option"));
				month.append($('<option>', {value:""}).text("Please select an option"));
				day.append($('<option>', {value:""}).text("Please select an option"));
				
				$.each(order, function(key, value) { //loop through json array and populate the year list
					year.append($('<option>', {
						value: value
					}).text(value));
				});
			} //end of else
		}, //end of success
		error: function() {
			console.log("There is an error in update_year_and_clear_month");
		},
		complete: function() {
			loading.hide(); //hide the loading element
		}
	}); //end of ajax call
	return false;
} //end of update_year_and_clear_month


function update_month(month_element, year_element, day_element, loading_pic) {
	var $sp = $('#solution_param'); //make a variable to hold solution param
	
	var comp = $('#component_param').val();
	var solution = $sp.val(); //grab parameter values
	var year = year_element.val();

	var temp_url = replace_url + "GenerateMonthList4/";
	temp_url+=solution +"/";
	temp_url+=comp + "/";
	temp_url+=year;

	month_element.empty(); //empty the month options
	day_element.empty(); //empty the day options

	$.ajax({
		beforeSend: function() {
			loading_pic.show(); //show loading animation
		},
		url: temp_url,
		type: 'GET',
		dataType: 'json',
		success: function(order) {
			month_element.append($('<option>', {value:""}).text("Please select an option"));
			day_element.append($('<option>', {value:""}).text("Please select an option"));

			$.each(order, function(key, value) { //loop through and add corresponding options with text and value of option from the JSON array
				month_element.append($('<option>', {
					value: value
				}).text(value));
			});
		},
		error: function() {
			console.log("There is an error in update_month()");
		},
		complete: function() {
			loading_pic.hide(); //hide loading animation
		}
	}); //end of ajax call(document.ready())
}//end of update_month()


function update_days(day_element, month_element, year_element, loading_pic){
	var month = month_element.val();
	var year = year_element.val();
	
	var temp_url = replace_url + "GenerateDays7/" + year + "/" + month;
	
	day_element.empty();//empty the day options

	$.ajax({
		beforeSend: function() {
			loading_pic.show(); //show loading animation
		},
		url: temp_url,
		type: 'GET',
		dataType: 'json',
		success: function(order) {
			// month_element.append(new Option("Please select an option", "")); //on success, add the string
			day_element.append($('<option>', {value:""}).text("Please select an option"));

			$.each(order, function(key, value) { //loop through and add corresponding options with text and value of option from the JSON array
				day_element.append($('<option>', {
					value: value
				}).text(value));
			});
		},
		error: function() {
			console.log("There is an error in update_days()");
		},
		complete: function() {
			loading_pic.hide(); //hide loading animation
		}
	}); //end of ajax call
}//end of update_days()


function SubmitEnableOrDisable(){//Calls FormIsFilled to see if all the options are filled and then will disable if it is not full and enable if it is
	if(FormIsFilled()){
		document.getElementById("column").disabled = false;
		document.getElementById("line").disabled = false;
	}
	else{
		document.getElementById("column").disabled = true;
		document.getElementById("line").disabled = true;
	}
}

function FormIsFilled() { //checks to see whether or not the options are NULL
	if ($('#solution_param').val() != null && $('#component_param').val() != null && $('#yearparam_start').val() != null && $('#monthparam_start').val() != null && $('#dayparam_start').val() != null && $('#yearparam_end').val() != null && $('#monthparam_end').val() != null && $('#dayparam_end').val() != null 

		&& $('#solution_param').val() != "" && $('#component_param').val() != ""  && $('#yearparam_start').val() != "" && $('#monthparam_start').val() != "" && $('#dayparam_start').val() !="" &&  $('#yearparam_end').val() != "" && $('#monthparam_end').val() != "" && $('#dayparam_end').val() != ""
	) {
		return true;
	}
	return false;
}


function CompareDateHelper(start_day, start_month, start_year, end_day, end_month, end_year) { //Returns true if start date is before end date, false if start date is later than end
	if (start_year.val() > end_year.val()) { // compare the years, if start year is later than end year, return false
		return false;
	}
	 else if (start_year.val() == end_year.val()) {
		var start_month_integer = month2Int(start_month.val()); //convert the word form of the month to an integer (ex: FEB -> 2)
		var end_month_integer = month2Int(end_month.val());
		if (start_month_integer < end_month_integer) { //compare the 2 months that have been converted to integers
			return true;
		}
		else {
			if(start_month_integer == end_month_integer){//same month
				if(start_day.val() <= end_day.val() ){
					return true;
				}
				return false;
			}
			return false;
		}//end of else
	} else {
		return true;
	}
}


function month2Int(month_var) { //Given a month as a string, it will convert it to an integer
	switch (month_var) {
		case "JAN":
			return 1;
		case "FEB":
			return 2;
		case "MAR":
			return 3;
		case "APR":
			return 4;
		case "MAY":
			return 5;
		case "JUN":
			return 6;
		case "JUL":
			return 7;
		case "AUG":
			return 8;
		case "SEP":
			return 9;
		case "OCT":
			return 10;
		case "NOV":
			return 11;
		case "DEC":
			return 12;
			break;
		default:
			return 0;
	}
}

function CompareDate() { //this is just a wrapper function that calls a helper function
	return CompareDateHelper( $('#dayparam_start'), $('#monthparam_start'), $('#yearparam_start'), $('#dayparam_end'), $('#monthparam_end'), $('#yearparam_end'));
}