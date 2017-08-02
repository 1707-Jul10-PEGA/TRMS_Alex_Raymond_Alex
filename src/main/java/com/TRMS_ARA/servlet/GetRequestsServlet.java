package com.TRMS_ARA.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.EmployeeMessageDao;
import model.message.EmployeeMessage;
import model.message.EmployeeMessages;

@WebServlet("/getRequests")
public class GetRequestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetRequestsServlet() {
        super();
    }
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int eId = (int)request.getSession().getAttribute("employeeId");
		EmployeeMessageDao dao = new EmployeeMessageDao();
		PrintWriter pw = response.getWriter();
		try {
			EmployeeMessage[] emsg = dao.getMessages(eId);
			if (emsg == null) {
				pw.write("");
			}
			else {
				EmployeeMessages emsgs= new EmployeeMessages(dao.getMessages(eId));
				response.setContentType("application/json");
				
				String emsgsString = (new ObjectMapper()).writeValueAsString(emsgs);

				pw.print(emsgsString);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
