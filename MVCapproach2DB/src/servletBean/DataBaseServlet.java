package servletBean;

import java.io.IOException;
/*
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.lang.String;

import com.xerox.isp.volumereports.VolumeReportBeanLocal;
import com.xerox.isp.volumereports.VolumeReportBean;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
*/
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DataBaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataBaseServlet() {
	}

	public void init() throws ServletException {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//not even used...
		
/*
		//following lines are messing with JSP 
		String temp = "First Report!";
		request.setAttribute("text", temp);

		String solution_id = request.getParameter("solution_param");
		String component_id = request.getParameter("component_param");

		String month = request.getParameter("monthparam_start");
		String year = request.getParameter("yearparam_start");

		String month_end = request.getParameter("monthparam_end");
		String year_end = request.getParameter("yearparam_end");

//		String first_submit = request.getParameter("FIRST");
		String second_submit = request.getParameter("SECOND");
		
	
		
		//beginning of processing the display information for 2nd web page
		if (second_submit != null) {//we are going to the second web page
			System.out.println("We are at the second page!!");
			VolumeReportBean report2bean = new VolumeReportBean();
			//public void generateReportTwo(Boolean DisplayChart, String solution_id, String month_start, String year_start, String month_end, String year_end)
			if(solution_id!=null){
				report2bean.generateReportTwo(true, solution_id, month, year, month_end, year_end);
			}
			else{
				report2bean.generateReportTwo(false, solution_id, month, year, month_end, year_end);
			}
		
			request.setAttribute("report2bean", report2bean);//sending the BigList to the object
			RequestDispatcher rd = request.getRequestDispatcher("Second.jsp");
			System.out.print("Forwarding to the second page");
			rd.forward(request, response);
			
			return;
		}
		
	
//		else{//first_submit has been chosen
			System.out.println("We are at the first page!!");
			VolumeReportBean report1bean = new VolumeReportBean();
			if (solution_id!=null){
//				System.out.println("Month in DataBaseServlet is:" + month);
				report1bean.generateReportOne(true, solution_id, component_id, month, year);
			}
			else{
				report1bean.generateReportOne(false, solution_id, component_id, month, year); 
			}
		
			request.setAttribute("report1bean", report1bean);
			RequestDispatcher rd_one = request.getRequestDispatcher("NewFile.jsp");
			System.out.print("Forwading to the first page");
			rd_one.forward(request,response);
			
			return;
			*/
//		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
	}
}
