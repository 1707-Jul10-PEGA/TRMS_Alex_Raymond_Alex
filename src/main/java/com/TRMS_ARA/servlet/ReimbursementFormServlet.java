package com.TRMS_ARA.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.RequestorDao;

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
		Double cost = Double.parseDouble(request.getParameter("price"));
		String gradingFormat = request.getParameter("format");
		int eventType = Integer.parseInt(request.getParameter("type"));
		String workRelated = request.getParameter("explain");
		
		RequestorDao requestDao = new RequestorDao();
		
		boolean put;
		
		try {
			put = requestDao.submitReimbursement(eId, startDate, startTime, location, description, cost, gradingFormat, eventType, workRelated);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			put = false;
			e.printStackTrace();
		}
		
		if (put){
			response.sendRedirect("formsuccess.html");
		} else {
			response.getWriter().write("Submission failed: please try again.");
		}
		
		//eId, startDate, startTime, location, description, cost, gradingFormat, eventType, workRelated
		
	}
	
}
