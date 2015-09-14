package com.xerox.isp.volumereports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.lang.Integer;
import javax.ejb.Stateless;
import com.xerox.isp.volumereports.ReportOne;

/**
 * Session Bean implementation class VolumeReportBean
 */
@Stateless
public class VolumeReportBean implements VolumeReportBeanLocal {
	
	
	private static String host = ;//Removed for Xerox privacy reasons
	private static String port = ;//Removed for Xerox privacy reasons
	private static String service_name = ;//Removed for Xerox privacy reasons
	
	private static String id = ;//Removed for Xerox privacy reasons
	private static String pass = ;//Removed for Xerox privacy reasons
		
	private static String first_param= ;//Removed for Xerox privacy reasons
	private static String second_param = ;//Removed for Xerox privacy reasons
	private static String third_param = ;//Removed for Xerox privacy reasons
	
	private static Connection DriverManager_getConnectionHelper(String first_param, String second_param, String third_param){//this functions makes it easy to get a connection
		try{
			Connection temp_connection = DriverManager.getConnection(first_param, second_param, third_param);
			return temp_connection;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}//end of DriverManager_getConnectionHelper
	

	public static ArrayList<String> generateSolutionList(){//generates solution list for Report 1
		ArrayList<String> tempSolList = new ArrayList<String>();
		String unique_sol_query = "select distinct(SOLUTION_ID) from ISPAUDIT.AUDIT_METRIC";//Query to execute
		try{
			Connection conne = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Get a Connection
			Statement stmt = conne.createStatement();
			tempSolList = GrabDistinctSolutionList(unique_sol_query, stmt);//calls helper function to populate tempSolList
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return tempSolList;
	}//end of generateSolutionList
	

	public static ArrayList<String> generateComponentList(String solution){//generates component list for Report 1
		ArrayList<String> ListOfComponent_IDS = new ArrayList<String>();//Declare an arraylist to hold strings
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//create connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	
	
			//Create SQL query
			String Query = "select distinct(COMPONENT_ID) from ISPAUDIT.AUDIT_METRIC where SOLUTION_ID = '";
			Query+=solution;
			Query+="'";
			
			rs_one = stmt_one.executeQuery(Query);//fill resultSet with Query
			
			while ( rs_one.next() ){//loop through the result set
				String add_this = rs_one.getString(1);//extract the 1st column from the ResultSet
				ListOfComponent_IDS.add( add_this);//add to ListOfComponent_IDS
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return ListOfComponent_IDS;
	}//end of generateComponentList
	
	
	public static ArrayList<String> generateYearList(String solution, String component){//generates year list for Report1 
		ArrayList<String> ListOfYears = new ArrayList<String>();//Declare an ArrayList that will be used later
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//get a connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	

			//create SQL query
			String Query = "select min(to_char(metric_start_time, 'yyyy')) from ISPAUDIT.AUDIT_METRIC where solution_id = '";
			Query += solution;
			Query += "' and component_id ='";
			Query += component;
			Query += "' and metric_name = 'Daily_Entry_Count' group by to_char(METRIC_START_TIME,'yyyy')";
			
			rs_one = stmt_one.executeQuery(Query);//Execute the Query
			
			while ( rs_one.next() ){//Loop through the ResultSet
				String add_this = rs_one.getString(1);//get the first column
				ListOfYears.add(add_this);//Add it to the ArrayList
			}
			Collections.sort(ListOfYears);//Sort the years
			return ListOfYears;//Returns the years
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}//end of generateYearList
	
	
	public static ArrayList<String> generateYearListForReport2(String solution){//generateYearListForReport2
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Get Connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	
			/*
			select min(to_char(metric_start_time, 'yyyy')) from audit_metric 
			where solution_id = 'S0224_YankeeSOFConfirmServiceESB'
			and METRIC_NAME = 'Daily_Entry_Count'
			group by to_char(METRIC_start_TIME,'yyyy')
			order by to_char(METRIC_start_TIME,'yyyy');
			*/
			String Query = "select min(to_char(metric_start_time, 'yyyy')) from ISPAUDIT.AUDIT_METRIC ";
			Query += "where solution_id = '";
			Query += solution;
			Query += "' and metric_name = 'Daily_Entry_Count' group by to_char(METRIC_start_TIME,'yyyy') order by to_char(METRIC_start_TIME,'yyyy')";
	
			rs_one = stmt_one.executeQuery(Query);
			ArrayList<String> ListOfYears2 = new ArrayList<String>();//Create an ArrayList that we will fill and return later
		
			while ( rs_one.next() ){//loop through result set to grab the corresponding values
				String add_this = rs_one.getString(1);//Grab the first column from the ResultSet
				ListOfYears2.add(add_this);//Add it to the ArrayList
			}
			return ListOfYears2;
		}

		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}//end of generateYearListForReport2
	
	public static ArrayList<String> generateYearListForReport3(String solution, String component){//generateYearListForReport3 
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Get Connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	
			/*
			select min(to_char(metric_start_time, 'yyyy')) from audit_metric 
			where solution_id = 'S0224_YankeeSOFConfirmService'
			and METRIC_NAME = 'Daily_Entry_Count'
			group by to_char(METRIC_start_TIME,'yyyy')
			order by to_char(METRIC_start_TIME,'yyyy');
			*/
			String Query = "select min(to_char(metric_start_time, 'yyyy')) from ISPAUDIT.AUDIT_METRIC ";
			Query += "where solution_id = '";
			Query += solution;
			Query += "' and component_id = '";
			Query += component;
			Query += "' and metric_name = 'Hourly_Error_Count' group by to_char(METRIC_start_TIME,'yyyy') order by to_char(METRIC_start_TIME,'yyyy')";
	
			rs_one = stmt_one.executeQuery(Query);//Execute the Query
			ArrayList<String> ListOfYears2 = new ArrayList<String>();//Create an ArrayList that you will fill and return later
		
			while ( rs_one.next() ){//loop through result set to grab the corresponding values
				String add_this = rs_one.getString(1);//Grab the first column from the ResultSet
				ListOfYears2.add(add_this);//Add it to the ArrayList
			}
			return ListOfYears2;
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}//end of generateYearListForReport3

	
	
	public static ArrayList<String> generateCompListForReport3(String solution){//generate year list for Report2
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Get Connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	
			/*
			select min(to_char(metric_start_time, 'yyyy')) from audit_metric 
			where solution_id = 'S0224_YankeeSOFConfirmServiceESB'
			and METRIC_NAME = 'Daily_Entry_Count'
			group by to_char(METRIC_start_TIME,'yyyy')
			order by to_char(METRIC_start_TIME,'yyyy');
			*/
			String Query = "select component_id from ISPAUDIT.AUDIT_METRIC ";
			Query += "where solution_id = '";
			Query += solution;
			Query += "' and metric_name = 'Hourly_Error_Count' group by component_id order by component_id";
	
			rs_one = stmt_one.executeQuery(Query);//Execute that Query
			ArrayList<String> ListOfYears2 = new ArrayList<String>();//Create an arraylist that you will add later
		
			while ( rs_one.next() ){//loop through result set to grab the corresponding values
				String add_this = rs_one.getString(1);//Get the first column 
				ListOfYears2.add(add_this);//Add it to the ArrayList
			}
			return ListOfYears2;//return that ArrayList
		}

		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}//end of generateYearListForReport2

	public static ArrayList<String> generateMonthList(String solution, String component, String year){//generates list of months for Report1
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Get Connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	

			//generate SQL query
			String Query = "select to_char(metric_start_time, 'mm') from ISPAUDIT.AUDIT_METRIC where solution_id = '";
			Query += solution;
			Query += "' and component_id ='";
			Query += component;
			Query += "' and METRIC_NAME = 'Daily_Entry_Count' and to_char(METRIC_START_TIME,'yyyy') = '";
			Query += year; 
			Query += "' group by to_char(METRIC_START_TIME,'mm')";

			rs_one = stmt_one.executeQuery(Query);//Execute the Query
		
			ArrayList<String> ListOfMonths = new ArrayList<String>();//Create an ArrayList
			while ( rs_one.next() ){//getting the list on 'mm' format ex: jun is: 06
				String add_this = rs_one.getString(1);//Grab the first column 
				ListOfMonths.add( add_this);//add it to the ArrayList 
			}
			
			Collections.sort(ListOfMonths);//sort the numbers that are in 'mm' format
			return Convert2Month(ListOfMonths);//convert 'mm' format to months
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}//end of generateMonthList
	
	public static ArrayList<String> generateMonthList2(String solution, String start_year){//generates list of months for Report2
		/*
		SELECT to_char(metric_start_time, 'mm')
		FROM audit_metric
		WHERE solution_id = 'S0224_YankeeSOFConfirmServiceESB'
		AND metric_name = 'Daily_Entry_Count'
		AND to_char(METRIC_START_TIME, 'yyyy') = '2015'
		GROUP BY to_char(METRIC_start_TIME, 'mm')
		ORDER BY to_char(METRIC_start_TIME, 'mm')
		*/
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Create Connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	

			//form sql query
			String Query = "select to_char(metric_start_time, 'mm') from ISPAUDIT.AUDIT_METRIC where solution_id = '";
			Query += solution;
			Query += "' and metric_name = 'Daily_Entry_Count' and to_char(metric_start_time, 'yyyy') = '";
			Query += start_year;
			Query += "' group by to_char(metric_start_time, 'mm') order by to_char(metric_start_time, 'mm')";

			rs_one = stmt_one.executeQuery(Query);//Execute the Query
		
			ArrayList<String> ListOfMonths = new ArrayList<String>();//Create an ArrayList
			while ( rs_one.next() ){//getting the list in 'mm' format ex: jun is: 06
				String add_this = rs_one.getString(1);//Grab the first column
				ListOfMonths.add( add_this);//Add it to the ArrayList
			}
			
			Collections.sort(ListOfMonths);//sort the numbers that are in 'mm' format
			return Convert2Month(ListOfMonths);//convert 'mm' format to months
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}//end of generateMonthList2
	
	
	/*
	 * 
	 * SELECT to_char(metric_start_time, 'mm')
		FROM ispaudit.audit_metric
		WHERE solution_id = 'S0224_YankeeSOFConfirmServiceESB'
    	AND component_id = 'YANKEE_WMS_MPPF_SOF_CONFIRM'
		AND metric_name = 'Hourly_Error_Count'
		AND to_char(METRIC_START_TIME, 'yyyy') = '2014'
		GROUP BY to_char(METRIC_start_TIME, 'mm')
		ORDER BY to_char(METRIC_start_TIME, 'mm')
		 */
	
	public static ArrayList<String> generateMonthList3(String solution, String component, String start_year){//generates list of months for Report7
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Create a Connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	

			//form sql query
			String Query = "select to_char(metric_start_time, 'mm') from ISPAUDIT.AUDIT_METRIC where solution_id = '";
			Query += solution;
			Query += "' and component_id ='";
			Query += component;
			Query += "' and metric_name = 'Hourly_Error_Count' and to_char(metric_start_time, 'yyyy') = '";
			Query += start_year;
			Query += "' group by to_char(metric_start_time, 'mm') order by to_char(metric_start_time, 'mm')";
			
			rs_one = stmt_one.executeQuery(Query);//Execute the Query
		
			ArrayList<String> ListOfMonths = new ArrayList<String>();//Create an ArrayList
			while ( rs_one.next() ){//getting the list in 'mm' format ex: jun is: 06
				String add_this = rs_one.getString(1);//Grab the first column
				ListOfMonths.add( add_this );//add it to the ArrayList
			}
			
			Collections.sort(ListOfMonths);//sort the numbers that are in 'mm' format
			return Convert2Month(ListOfMonths);//convert 'mm' format to months
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}//end of generateMonthList7
	

	public static ArrayList<String> generateDayList3(String solution, String component, String start_year, String start_month){//generate list of days for Report 7
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Grab the Connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	

			//form sql query
			String Query = "select to_char(metric_start_time, 'dd') from ISPAUDIT.AUDIT_METRIC where solution_id = '";
			Query += solution;
			Query += "' and component_id ='";
			Query += component;
			Query += "' and metric_name = 'Hourly_Error_Count' and to_char(metric_start_time, 'yyyy') = '";
			Query += start_year;
			Query += "' and to_char(metric_start_time, 'MON') ='";
			Query += start_month;
			Query += "' group by to_char(metric_start_time, 'dd') order by to_char(metric_start_time, 'dd')";
			
			rs_one = stmt_one.executeQuery(Query);//Execute the Query
		
			ArrayList<String> ListOfMonths = new ArrayList<String>();//Create an ArrayList
			while ( rs_one.next() ){//getting the list in 'mm' format ex: jun is: 06
				String add_this = rs_one.getString(1);//Grab the first column
				ListOfMonths.add( add_this);//Add it to the ArrayList
			}
			
			Collections.sort(ListOfMonths);//sort the numbers that are in 'mm' format
			return ListOfMonths;
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<ReportFive> generateReportThird(String solution, String component, String start_date, String end_date){//generateReportThree
		try {
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Grab the first connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_count;

			Connection conn_two = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Grab the second connection
			Statement stmt_two = conn_two.createStatement();
			ResultSet rs_error;
		
			//The following SQL query is what we will execute, note: we will change metric_name equal to "Hourly_Entry_Count" for the variable CountQuery
			/*
			SELECT Sum(metric_value) 
			FROM   ispaudit.audit_metric 
			WHERE  solution_id = 'S0228_CrmInquiryServiceWeb' 
			       AND component_id = 'getHistory' 
			       AND metric_name = 'Hourly_Error_Count' 
			       AND metric_start_time >= '24-APR-15' 
			       AND metric_start_time < '28-JUN-15' 
			       AND To_char(New_time(metric_start_time, 'GMT', 'EDT'), 'hh24') = '19'
			*/
			
//			System.out.println("The start date is:"  + start_date);
//			System.out.println("The end date is:"  + end_date);
			
			String ErrorQuery = "select sum(metric_value) from ispaudit.audit_metric where solution_id ='";//Query to grab the Hourly_Error_Counts
			ErrorQuery+= solution;
			ErrorQuery+="' and component_id = '";
			ErrorQuery+=component;
			ErrorQuery+="' and metric_name= 'Hourly_Error_Count' and metric_start_time >= '";
			ErrorQuery+= start_date;
			ErrorQuery+= "' and metric_start_time <= '";
			ErrorQuery+= end_date;
			ErrorQuery+= "' and to_char(new_time(metric_start_time, 'GMT', 'EDT'), 'hh24') = '";
			
			
			String CountQuery = "select sum(metric_value) from ispaudit.audit_metric where solution_id ='";//Query to grab the Hourly_Entry_Counts
			CountQuery+= solution;
			CountQuery+="' and component_id = '";
			CountQuery+=component;
			CountQuery+="' and metric_name= 'Hourly_Entry_Count' and metric_start_time >= '";
			CountQuery+= start_date;
			CountQuery+= "' and metric_start_time <= '";
			CountQuery+= end_date;
			CountQuery+= "' and to_char(new_time(metric_start_time, 'GMT', 'EDT'), 'hh24') = '";
			
			ArrayList<ReportFive> ReportFiveList = new ArrayList<ReportFive>();	//Create an ArrayList that will be returned by the function
			
			for(int i=0; i<24; i++){
				String two_decimal_format = new DecimalFormat("00").format(i);//convert i to decimal format
				String hour = two_decimal_format + "'";//add a tick mark at the end, to satisfy SQL Query Statement
				
				String CompleteErrorQuery = ErrorQuery + hour;//Complete the Error Query, 
				String CompleteCountQuery = CountQuery + hour;//Complete the Count Query,
				
				rs_count = stmt_one.executeQuery(CompleteCountQuery);//executeQuery (COUNT)
				rs_count.next();//Scroll through first result set
				String request_count = rs_count.getString(1);//get the first column
				
				ReportFive ReportFiveBean = new ReportFive();
				if( request_count != null){//then just add request count
					ReportFiveBean.setREQUEST_COUNT(request_count);//set the bean's request count
					ReportFiveList.add(ReportFiveBean);//add it to the list of beans
				}
				else{// request_count==null, then just add a request count of zero
					ReportFiveBean.setREQUEST_COUNT("0");//set the bean's request count to 0, since we received a null from the 
					ReportFiveList.add(ReportFiveBean);//add it to the lsit of beans
				}
				
				rs_error = stmt_two.executeQuery(CompleteErrorQuery);//executeQuery (ERROR)
				rs_error.next();//Scroll through first result set
				String error_count = rs_error.getString(1);//get the first column
				
				if( error_count != null){//the error count does not equal 0
					ReportFiveList.get(i).setERROR_COUNT(error_count);//Since a bean in this iteration was already created, we grab it from the list and set the error_count
				}
				else{//the error count is 0
					ReportFiveList.get(i).setERROR_COUNT("0");//if error_count == null, set the error_count to 0
				}
			
				//Following code adds am or pm to the time
				int est_time = (i);// the time will be 24hr format (0-23)
				est_time=est_time%12;//take the mod of it
			
				String actual_est_time = null;//actual_est_time will be a nice formatted time
				if(i>=0&&i<=11 ){//add the am extension, 
					if(i==0){
						actual_est_time = "12am";
					}
					else{
						actual_est_time = String.valueOf(est_time)+"am";
					}
				}
				else{//add the pm extension
					if(i==12){
						actual_est_time = "12pm";
					}
					else{
						actual_est_time = String.valueOf(est_time)+"pm";
					}
				}
				
				ReportFiveList.get(i).setHOUR(actual_est_time);//Grab the bean from this iteration(for) loop and set the time
				ReportFiveList.get(i).setSOLUTION_ID(solution);//set the solution id
				ReportFiveList.get(i).setCOMPONENT_ID(component);//set the component id
			}
			conn_one.close();//close connections
			conn_two.close();
			return ReportFiveList;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}//end of generateReportThird
	
	public static ArrayList<ReportOne> generateReportOne(String solution_id, String component_id, String year_start, String month_start){//Report 1
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Create Connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	
			
			String NiceDate_one = TurnToNiceDateStart(month_start, year_start);//returns a  date in this format, including the ', ('01-JAN-2015')

			String Query_one = CreateSqlQueryReport1(solution_id, component_id, NiceDate_one);//calls helper function to create the SQL query
			rs_one = stmt_one.executeQuery(Query_one);//Execute the Query

			ArrayList<ReportOne> ReportOneList = new ArrayList<ReportOne>();//Create an ArrayList
			while (rs_one.next()) {//loop through result set
				ReportOne tempReportOne = new ReportOne();//Declare new Report ONe
				tempReportOne.setSOLUTION_ID(rs_one.getString(1));//solution id is in column 1
				tempReportOne.setCOMPONENT_ID(rs_one.getString(2));//component id is in column 2
				tempReportOne.setDATE(rs_one.getString(3));//date is in column 3
				tempReportOne.setREQUEST_COUNT(rs_one.getString(4));//request count is in column 4

				ReportOneList.add(tempReportOne);
			}
			conn_one.close();//close connection
			return ReportOneList;
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}//end of generateReportOne
	
	
	
public static ArrayList<ReportTwo> generateReportTwo(String solution, String year_start, String month_start, String year_end, String month_end){
		try{
			Connection conne = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Get Connection
			Statement stmt = conne.createStatement();
			ResultSet rs;

			String StartDate = TurnToNiceDateStart(month_start, year_start);//returns a  date in this format, including the ', ('01-JAN-2015')
			String EndDate = TurnToNiceDateEnd(month_end, year_end, stmt);//returns a  date in this format, including the ', ('01-JAN-2015')
 
			String Query = CreateSqlQueryReport2(solution, StartDate, EndDate);//calls helper function to create the SQL query
			rs = stmt.executeQuery(Query);

			//populating reportOneList
			ArrayList<ReportTwo> TwoList = new ArrayList<ReportTwo>();
			while (rs.next()) {
				ReportTwo tempReportTwo = new ReportTwo();
				tempReportTwo.setSOLUTION_ID(rs.getString(1));
				tempReportTwo.setDATE(rs.getString(2));
				tempReportTwo.setREQUEST_COUNT(rs.getString(3));
				TwoList.add(tempReportTwo); //added it to the big list
			}
	
			conne.close();
			return TwoList;
		}
		catch (SQLException e){
			e.printStackTrace(); 
			return null;
		}
}//end of generateReportTwo less parameters


public static ArrayList<ReportFour> generateReportFour(String solution, String component, String start_date, String end_date) {
	try{
		Connection conne = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Create first connection
		Statement stmt = conne.createStatement();
		ResultSet rs_entry;
				
		Connection conn = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Create second connection
		Statement statement = conn.createStatement();
		ResultSet rs_error;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");//the Date will be in this format (similar to oracle date format)
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(start_date));//set the time with start_date and format of start_date
		String temp = sdf.format(c.getTime());// dt is now the new da
		temp=temp.toUpperCase();//set it to all upperCase
		
		Calendar d = Calendar.getInstance();//Same thing as above
		d.setTime(sdf.parse(end_date));
		String temp_end_date = sdf.format(d.getTime());
		temp_end_date=temp_end_date.toUpperCase();
	
		String QueryError = "select solution_id, component_id, metric_name, metric_start_time, metric_value from ispaudit.audit_metric where solution_id='";
		QueryError+=solution;
		QueryError+="' and metric_name = 'Daily_Error_Count' and component_id = '";
		QueryError+=component;
		QueryError+="' and metric_start_time='";
		String QueryError1 ="'  order by metric_start_time";
		
		
		String QueryEntry ="select solution_id, component_id, metric_name, metric_start_time, metric_value from ispaudit.audit_metric where solution_id='";
		QueryEntry+=solution;
		QueryEntry+="' and metric_name = 'Daily_Entry_Count' and component_id ='";
		QueryEntry+=component;
		QueryEntry+="' and metric_start_time='";
		String QueryEntry1 = "' order by metric_start_time";
		
		ArrayList<ReportFour> FourList = new ArrayList<ReportFour>();	
		
		while( temp.equals(temp_end_date)==false ){//stop when you reach the end_date
			String Final_Query_Error = QueryError + temp + QueryError1 ; //Incrementing date and combining it into our Query
			String Final_Query_Entry = QueryEntry + temp + QueryEntry1 ; //Incrementing date and combining it into our Query
			
			rs_entry = stmt.executeQuery(Final_Query_Entry);//Execute the Query
			ReportFour ReportFourBean = new ReportFour();

			if (rs_entry.next() == false){//If resultSet is empty
				ReportFourBean.setREQUEST_COUNT("0");//set the request count to 0
			}
			else{
				String entry_count = rs_entry.getString(5);//Grab Daily_Entry_Counts
				ReportFourBean.setREQUEST_COUNT(entry_count);
			}
			
			rs_error = statement.executeQuery(Final_Query_Error);//Execute the Query
			if ( rs_error.next() == false){//If resultSet is empty
				ReportFourBean.setERROR_COUNT("0");
			}
			else{
				String error_count = rs_error.getString(5);
				ReportFourBean.setERROR_COUNT(error_count);
			}
			
			ReportFourBean.setDATE(temp);//setting the date
			ReportFourBean.setSOLUTION_ID(solution);//This is the solution
			ReportFourBean.setCOMPONENT_ID(component);//This is the component
			
			FourList.add(ReportFourBean);
			
			c.add(Calendar.DATE, 1); //Add 1 day
			temp = sdf.format(c.getTime()); //dt is now the new da
			temp=temp.toUpperCase();//Set the date to all upperCase, since the while loop comparison is in CAPS
			
		}//end of while loop
		conne.close();
		conn.close();
		
		return FourList;
	}
	catch (SQLException e){
		e.printStackTrace();
		return null;
	}
	catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
}//end of generateReportFour


/*
	15:25:10,413 INFO  [STDOUT] The date is:2015-07-06 00:00:00
	15:25:10,413 INFO  [STDOUT] The new Date is now: 2015-07-06
	
	look like this: 06-JUL-15
*/

private static ArrayList<String> GrabDistinctSolutionList(String Query, Statement stmt) {//helper function to grab list of solution_ids 
		try {
			ResultSet rs_loop = stmt.executeQuery(Query);
			ArrayList<String> Temp = new ArrayList<String>();
			
			while (rs_loop.next()) {
				String tempString = rs_loop.getString(1);
				if (tempString != null) {
					Temp.add(tempString);
				}
			}
			Collections.sort(Temp, String.CASE_INSENSITIVE_ORDER);//sort the solution ids
			return Temp;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
}//end of GrabDistinctForColumns

   	//helper function
private static String TurnToNiceDateStart(String month, String year) {
		// Given a month and year, it will turn into this format, ex:
		// '01-JAN-2015'
		String NiceDate = "'01-";
		NiceDate += month;
		NiceDate += "-";
		NiceDate += year;
		NiceDate += "'";
		return NiceDate;
}

private static String TurnToNiceDateEnd(String month, String year, Statement stmt) {//helper function
// Given a month and year, it will turn into this format, ex:
// '01-JAN-2015'

//		SELECT unique(LAST_DAY('1-Jun-2015')) from audit_metric 
		String Query = "SELECT unique(LAST_DAY('1-";
		Query += month + "-";
		Query += year +"'";
		Query += ")) from ISPAUDIT.AUDIT_METRIC";
		
		try{
			String result = null;
			ResultSet rs = stmt.executeQuery(Query);
			while (rs.next()){
				result = rs.getString(1);
				//the result is in this example format:'2015-06-30 00:00:00'
			}
			
			//Now that you have the last date of the corresponding month and year, just concatenate
			String day = result.substring(result.lastIndexOf("-")+1, result.indexOf(" "));
			String NiceDate = "'"  + day + "-" + month + "-" + year + "'";
			return NiceDate;
		}
		catch(SQLException e){
			e.printStackTrace();
			return null;
		}
}//end of TurnToNiceDateEnd
	
	public static ArrayList<String> GenerateCompList4(String solution) {//Generates the Component List for Report 4
		try {
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Get connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;

			String Query = "select distinct(component_id) from ispaudit.audit_metric where solution_id = '";
			Query += solution + "' and metric_name = 'Daily_Error_Count'";
			rs_one = stmt_one.executeQuery(Query);//Execute the Query

			ArrayList<String> TempCompList = new ArrayList<String>();
			String result = null;
			while (rs_one.next()) {
				result = rs_one.getString(1);//get the first column
				TempCompList.add(result);
				//the result is in this example format:'2015-06-30 00:00:00'
			}
			return TempCompList;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	} // end of GenerateCompList4
	
	public static ArrayList<String> GenerateYearList4(String solution, String component){ //Generates the Year List for Report 4
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Get Connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	
			
			String Query = "select min( to_char(metric_start_time, 'yyyy') ) from ispaudit.audit_metric where solution_id = '";
			Query+=solution + "' and component_id = '";
			Query+=component + "' and metric_name = 'Daily_Error_Count' group by to_char(METRIC_START_TIME, 'yyyy')";
			
			rs_one = stmt_one.executeQuery(Query);//Execute Query
		
			ArrayList<String> TempCompList = new ArrayList<String>();
			String result = null;
			while (rs_one.next()){
				result = rs_one.getString(1);//Grab the First Column
				TempCompList.add(result);
				//the result is in this example format:'2015-06-30 00:00:00'
			}
			Collections.sort(TempCompList);//Sort the component_ids
			return TempCompList;
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}//end of GenerateYearList4
	
	
	public static ArrayList<String> GenerateMonthList4(String solution, String component, String year){
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Grab the Connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	
		
			/*
			SELECT
			  MIN(to_char(metric_start_time, 'mm'))
			FROM ispaudit.audit_metric
			WHERE solution_id = 'S0228_CrmInquiryServiceWeb'
			AND component_id = 'getHistory'
			AND metric_name = 'Daily_Error_Count'
			AND to_char(metric_start_time, 'yyyy') = '2015'
			GROUP BY to_char(METRIC_START_TIME, 'mm')
			*/
			
			String Query = "SELECT min(to_char(metric_start_time, 'mm')) from ispaudit.audit_metric where solution_id = '";
			Query+=solution;
			Query+="' and component_id = '";
			Query+=component;
			Query+="' and metric_name = 'Daily_Entry_Count' and to_char(metric_start_time, 'yyyy') = '";
			Query+=year;
			Query+="' group by to_char(METRIC_START_TIME, 'mm')";
			
			rs_one = stmt_one.executeQuery(Query);//Execute Query
		
			ArrayList<String> TempCompList = new ArrayList<String>();
			String result = null;
			while (rs_one.next()){
				result = rs_one.getString(1);
				TempCompList.add(result);
				//the result is in this example format:'2015-06-30 00:00:00'
			}
			Collections.sort(TempCompList);//sort the months
			return Convert2Month(TempCompList);//Convert to month (numbers to actual month abbreviations)
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}//end of GenerateMonthList4
	
	
	private static String GetLastDateOfMonth(String month, String year){
		try{
			Connection conn_one = DriverManager_getConnectionHelper(first_param, second_param, third_param);//Get Connection
			Statement stmt_one = conn_one.createStatement();
			ResultSet rs_one;	
			
			String date = "'1-" + month + "-" + year + "'";
			String Query = "SELECT unique(LAST_DAY(" + date + ")) from ispaudit.audit_metric";
			
			rs_one = stmt_one.executeQuery(Query);//Execute the Query
			rs_one.next();
			
			String last_date = rs_one.getString(1);
//			2015-01-31 00:00:00,        31 is the last day
			String last_day = last_date.substring(last_date.lastIndexOf("-")+1 , last_date.indexOf(" "));//this will be used to grab the last day
			return last_day;
		}
		catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}// end of GetLastDateOfMonth
	
	public static ArrayList<String> CreateListOfDays2LastDateOfMonth(String month, String year){//Will fill from day 1 to last day of month
		String last_day = GetLastDateOfMonth(month, year);
		ArrayList<String> tempListOfDays = new ArrayList<String>();
			
		int last_day_int = Integer.parseInt(last_day);  				//change from int to string
		for(int i=1; i<last_day_int; i++){
			String converted_int = new DecimalFormat("00").format(i);	//make sure to have 2 decimal places
			tempListOfDays.add(converted_int);							// add it to the list
		}
		return tempListOfDays;
	}//end of CreateListOfDays2LastDateOfMonth

	//helper functions
	private static String CreateSqlQueryReport1(String Solution_id, String Component_id, String NiceDate) { // NiceDate is in '01-JAN-2015' format
		/*
		 * select SOLUTION_ID, COMPONENT_ID, to_char(METRIC_END_TIME,'yyyy-mm')
		 * as MONTH, sum(METRIC_VALUE) as REQUEST_COUNT from AUDIT_METRIC where
		 * METRIC_START_TIME >= '01-JAN-2015' and METRIC_END_TIME <= (SYSDATE)
		 * and SOLUTION_ID = 'S0228_CrmInquiryServiceWeb' and COMPONENT_ID =
		 * 'getHistory' and METRIC_NAME = 'Daily_Entry_Count' group by
		 * SOLUTION_ID, COMPONENT_ID, to_char(METRIC_END_TIME,'yyyy-mm') order
		 * by SOLUTION_ID, COMPONENT_ID, to_char(METRIC_END_TIME,'yyyy-mm')
		 */
		String SqlQuery = "select SOLUTION_ID, COMPONENT_ID, to_char(METRIC_END_TIME,'yyyy-mm') as MONTH, sum(METRIC_VALUE) as REQUEST_COUNT from ISPAUDIT.AUDIT_METRIC";
		SqlQuery += " where METRIC_START_TIME >= ";
		SqlQuery += NiceDate;// add the start date
		SqlQuery += " and METRIC_END_TIME <= (SYSDATE) and SOLUTION_ID = '";
		SqlQuery += Solution_id; //add the solution id
		SqlQuery += "' and COMPONENT_ID = '";
		SqlQuery += Component_id; // add the component id
		SqlQuery += "' and METRIC_NAME = 'Daily_Entry_Count' group by SOLUTION_ID, COMPONENT_ID, to_char(METRIC_END_TIME,'yyyy-mm') order by";
		SqlQuery += " SOLUTION_ID, COMPONENT_ID, to_char(METRIC_END_TIME,'yyyy-mm')";
		return SqlQuery;
	}

	//helper function
	private static String CreateSqlQueryReport2(String Solution_id, String NiceDate_start, String NiceDate_end) { // NiceDate is in '01-JAN-2015' format
		/* REPORT TWO
		select SOLUTION_ID, to_char(METRIC_END_TIME,'yyyy-mm') as MONTH, sum(METRIC_VALUE) as REQUEST_COUNT
		from AUDIT_METRIC
		where METRIC_START_TIME >= '01-JAN-2014'
		and METRIC_END_TIME <= '31-DEC-2014'
		and SOLUTION_ID = 'S0228_CrmInquiryServiceWeb'
		and METRIC_NAME = 'Daily_Entry_Count'
		group by SOLUTION_ID, to_char(METRIC_END_TIME,'yyyy-mm')
		order by SOLUTION_ID, to_char(METRIC_END_TIME,'yyyy-mm');
		*/

		String SqlQuery = "select SOLUTION_ID, to_char(METRIC_END_TIME,'yyyy-mm') as MONTH, sum(METRIC_VALUE) as REQUEST_COUNT from ISPAUDIT.AUDIT_METRIC";
		SqlQuery += " where METRIC_START_TIME >= ";
		SqlQuery += NiceDate_start; // add the start date
		SqlQuery += " and METRIC_END_TIME <= ";
		SqlQuery += NiceDate_end; //add the end date
		SqlQuery += " and SOLUTION_ID = '";
		SqlQuery += Solution_id; //add the solution id
		SqlQuery += "' and METRIC_NAME = 'Daily_Entry_Count' group by SOLUTION_ID, to_char(METRIC_END_TIME,'yyyy-mm') order by";
		SqlQuery += " SOLUTION_ID, to_char(METRIC_END_TIME,'yyyy-mm')";
		return SqlQuery;
	}//CreateSqlQueryReport2
	
	private static ArrayList<String> Convert2Month(ArrayList<String> ListOfMonths){//converts an array of Strings of numbers to their corresponding months
		ArrayList<String> ReturnThis = new ArrayList<String>();
		for (int i = 0; i < ListOfMonths.size(); i++) {
			if (ListOfMonths.get(i).equals("01")) {
				ReturnThis.add("JAN");
			}
			else if (ListOfMonths.get(i).equals("02")) {
				ReturnThis.add("FEB");
			}
			else if (ListOfMonths.get(i).equals("03")) {
				ReturnThis.add("MAR");
			}
			else if (ListOfMonths.get(i).equals("04")) {
				ReturnThis.add("APR");
			}
			else if (ListOfMonths.get(i).equals("05")) {
				ReturnThis.add("MAY");
			}
			else if (ListOfMonths.get(i).equals("06")) {
				ReturnThis.add("JUN");
			}
			else if (ListOfMonths.get(i).equals("07")) {
				ReturnThis.add("JUL");
			}
			else if (ListOfMonths.get(i).equals("08")) {
				ReturnThis.add("AUG");
			}
			else if (ListOfMonths.get(i).equals("09")) {
				ReturnThis.add("SEP");
			}
			else if (ListOfMonths.get(i).equals("10")) {
				ReturnThis.add("OCT");
			}
			else if (ListOfMonths.get(i).equals("11")) {
				ReturnThis.add("NOV");
			}
			else {
				ReturnThis.add("DEC");
			}
		}//end of for loop
		return ReturnThis;
	}//end of Convert2Month
	
}
