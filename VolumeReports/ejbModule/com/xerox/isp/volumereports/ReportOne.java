package com.xerox.isp.volumereports;

public class ReportOne {
	String SOLUTION_ID;
	String COMPONENT_ID;
	String DATE;
	String REQUEST_COUNT;

	public ReportOne() {
		// TODO Auto-generated constructor stub
		this.SOLUTION_ID = "SOLUTION_ID";
		this.COMPONENT_ID = "COMPONENT_ID";
		this.DATE = "DATE";
		this.REQUEST_COUNT = "REQUEST_COUNT";
	}

	public String getSOLUTION_ID() {
		return SOLUTION_ID;
	}

	public void setSOLUTION_ID(String sOLUTION_ID) {
		SOLUTION_ID = sOLUTION_ID;
	}

	public String getDATE() {
		return DATE;
	}

	public void setDATE(String dATE) {
		DATE = dATE;
	}

	public String getCOMPONENT_ID() {
		return COMPONENT_ID;
	}

	public void setCOMPONENT_ID(String cOMPONENT_ID) {
		COMPONENT_ID = cOMPONENT_ID;
	}

	public String getREQUEST_COUNT() {
		return REQUEST_COUNT;
	}

	public void setREQUEST_COUNT(String rEQUEST_COUNT) {
		REQUEST_COUNT = rEQUEST_COUNT;
	}
}
