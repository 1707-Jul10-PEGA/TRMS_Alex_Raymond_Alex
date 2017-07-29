package com.TRMS_ARA.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReimbursementFormServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3457214631478156496L;
	
	public ReimbursementFormServlet() {
		super();
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int eId = (Integer) request.getSession().getAttribute("employeeId");
		
		String startDate = request.getParameter("date");
		String startTime = request.getParameter("time");
		
		String location = request.getParameter("place");
		String description = request.getParameter("description");
		String explanation = request.getParameter("explain");
		Double cost = Double.parseDouble(request.getParameter("price"));
		String gradingFormat = request.getParameter("format");
		String eventType = request.getParameter("type");
		String workRelated = request.getParameter("explain");
		
	}
	
}
