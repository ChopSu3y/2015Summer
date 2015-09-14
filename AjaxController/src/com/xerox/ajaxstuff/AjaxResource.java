package com.xerox.ajaxstuff;
import java.util.ArrayList;

import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
/*
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Consumes;
*/
import com.google.gson.Gson;
import com.xerox.isp.volumereports.ReportFive;
import com.xerox.isp.volumereports.ReportFour;
import com.xerox.isp.volumereports.ReportOne;
import com.xerox.isp.volumereports.ReportTwo;
import com.xerox.isp.volumereports.VolumeReportBean;


@Path("/AjaxController")
public class AjaxResource {

	@GET()
	@Path("Report1/{solution}/{component}/{year}/{month}")
	@Produces("application/json")
	public String Report1(@PathParam("solution") String solution, @PathParam("component") String component, @PathParam("year") String year, @PathParam("month") String month){
		ArrayList<ReportOne> tempList = VolumeReportBean.generateReportOne(solution, component, year, month);
		String json = new Gson().toJson( tempList );
		return json;
	}
	

	@GET()
	@Path("Report2/{solution}/{start_year}/{start_month}/{end_year}/{end_month}")
	@Produces("application/json")
	public String Report2(@PathParam("solution") String solution, @PathParam("start_year") String start_year, @PathParam("start_month") String start_month,
			@PathParam("end_year") String end_year, @PathParam("end_month")  String end_month){
		ArrayList<ReportTwo> tempList = VolumeReportBean.generateReportTwo(solution, start_year, start_month, end_year, end_month);
		String json = new Gson().toJson( tempList );	
		return json;
	}
	
	@GET()
	@Path("Report3/{solution}/{component}/{start_date}/{end_date}")
	@Produces("application/json")
	public String Report3(@PathParam("solution") String solution, @PathParam("component") String component, @PathParam("start_date") String start_date, @PathParam("end_date") String end_date){
			ArrayList<ReportFive> tempList = VolumeReportBean.generateReportThird(solution, component, start_date, end_date);
			String json = new Gson().toJson( tempList );
			return json;
	}
	
	@GET()
	@Path("Report4/{solution}/{component}/{start_date}/{end_date}")
	@Produces("application/json")
	public String Report4(@PathParam("solution") String solution, @PathParam("component") String component, @PathParam("start_date") String start_date, @PathParam("end_date") String end_date){
		ArrayList<ReportFour> tempList = VolumeReportBean.generateReportFour(solution, component, start_date, end_date);
		String json = new Gson().toJson( tempList );
		return json;
	}

	@GET()
	@Path("GenerateSolutionList")
	@Produces("application/json")
	public String GenerateSolutionList(){
		ArrayList<String> tempList = VolumeReportBean.generateSolutionList();
		String json = new Gson().toJson( tempList );	
		return json;
	}
	

	@GET()
	@Path("GenerateComponentList/{solution}")
	@Produces("application/json")
	public String GenerateComponentList(@PathParam("solution") String solution){
		ArrayList<String> tempList = VolumeReportBean.generateComponentList(solution);
		String json = new Gson().toJson( tempList );
		return json;
	}
	
	@GET()
	@Path("GenerateYearList/{solution}/{component}")
	@Produces("application/json")
	public String GenerateYearList(@PathParam("solution") String solution, @PathParam("component") String component){
		ArrayList<String> tempList = VolumeReportBean.generateYearList(solution, component);
		String json = new Gson().toJson( tempList );	
		return json;
	}
	
	@GET()
	@Path("GenerateMonthList/{solution}/{component}/{year}")
	@Produces("application/json")
	public String GenerateMonthList(@PathParam("solution") String solution, @PathParam("component") String component, @PathParam("year") String year){
		ArrayList<String> tempList = VolumeReportBean.generateMonthList(solution, component, year);
		String json = new Gson().toJson( tempList );	
		return json;
	}
	
	@GET()
	@Path("GenerateYearListForReport2/{solution}")
	@Produces("application/json")
	public String GenerateYearListForReport2(@PathParam("solution") String solution){
		ArrayList<String> tempList = VolumeReportBean.generateYearListForReport2(solution);
		String json = new Gson().toJson( tempList );
		return json;	
	}
	
	@GET()
	@Path("GenerateYearListForReport3/{solution}/{component}")
	@Produces("application/json")
	public String GenerateYearListForReport3(@PathParam("solution") String solution, @PathParam("component") String component){
		ArrayList<String> tempList = VolumeReportBean.generateYearListForReport3(solution, component);
		String json = new Gson().toJson( tempList );
		return json;	
	}
	
	@GET()
	@Path("GenerateCompListForReport3/{solution}")
	@Produces("application/json")
	public String GenerateCompListForReport3(@PathParam("solution") String solution){
		ArrayList<String> tempList = VolumeReportBean.generateCompListForReport3(solution);
		String json = new Gson().toJson( tempList );
		return json;	
	}
	
	@GET()
	@Path("GenerateMonthList2/{solution}/{year}")
	@Produces("application/json")
	public String GenerateMonthList2(@PathParam("solution") String solution, @PathParam("year") String year){
		ArrayList<String> tempList = VolumeReportBean.generateMonthList2(solution, year);
		String json = new Gson().toJson( tempList );
		return json;	
	}
	
	@GET()
	@Path("GenerateDayList3/{solution}/{component}/{year}/{month}")
	@Produces("application/json")
	public String GenerateDayList3(@PathParam("solution") String solution, @PathParam("component") String component, @PathParam("year") String year, @PathParam("month") String month){
		ArrayList<String> tempList = VolumeReportBean.generateDayList3(solution, component, year, month);
		String json = new Gson().toJson( tempList );
		return json;	
	}
	
	@GET()
	@Path("GenerateMonthList3/{solution}/{component}/{year}")
	@Produces("application/json")
	public String GenerateMonthList3(@PathParam("solution") String solution, @PathParam("component") String component, @PathParam("year") String year){
		ArrayList<String> tempList = VolumeReportBean.generateMonthList3(solution, component, year);
		String json = new Gson().toJson( tempList );
		return json;	
	}
	
	

	@GET()
	@Path("GenerateCompList4/{solution}")
	@Produces("application/json")
	public String GenerateCompList4(@PathParam("solution") String solution){
		ArrayList<String> tempList = VolumeReportBean.GenerateCompList4(solution);
		String json = new Gson().toJson( tempList );
		return json;
	}
	
	@GET()
	@Path("GenerateYearList4/{solution}/{component}")
	@Produces("application/json")
	public String GenerateYearList4(@PathParam("solution") String solution, @PathParam("component") String component){
		ArrayList<String> tempList = VolumeReportBean.GenerateYearList4(solution, component);
		String json = new Gson().toJson( tempList );
		return json;
	}
	
	
	@GET()
	@Path("GenerateMonthList4/{solution}/{component}/{year}")
	@Produces("application/json")
	public String GenerateMonthList4(@PathParam("solution") String solution, @PathParam("component") String component, @PathParam("year") String year){
		ArrayList<String> tempList = VolumeReportBean.GenerateMonthList4(solution, component, year);
		String json = new Gson().toJson( tempList );
		return json;
	}

	
	@GET()
	@Path("GenerateDays3/{year}/{month}")
	@Produces("application/json")
	public String GenerateDays3(@PathParam("year") String year, @PathParam("month") String month){
			System.out.println("GenerateDays3 is being called");
			ArrayList<String> tempList = VolumeReportBean.CreateListOfDays2LastDateOfMonth(month,year);
			String json = new Gson().toJson( tempList );
			System.out.println("GenerateDays3 is done being called");
			return json;
	}
	
}//end of class