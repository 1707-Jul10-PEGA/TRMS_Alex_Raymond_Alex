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

import dao.EmployeeDao;
import model.reimbursement.RFView;
import model.reimbursement.RFViewRow;

@WebServlet("/getOwnForms")
public class GetOwnFormsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1640122821487082427L;

	public GetOwnFormsServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int eId = (int) request.getSession().getAttribute("employeeId");
		System.out.println(eId);
		EmployeeDao emDao = new EmployeeDao();

		RFViewRow[] rfOwnArray = null;

		try {
			rfOwnArray = emDao.getOwnForms(eId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PrintWriter pw = response.getWriter();

		if (rfOwnArray == null) {
			pw.print("");
		} 
		else {
			RFView rfv = new RFView(rfOwnArray);
			response.setContentType("application/json");

			String rfOwnListString = (new ObjectMapper()).writeValueAsString(rfv);

			pw.print(rfOwnListString);
		}
	}

}
