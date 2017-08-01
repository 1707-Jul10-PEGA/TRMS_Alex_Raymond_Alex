package com.TRMS_ARA.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.ApproverDao;
import model.reimbursement.RFViewRow;
import model.reimbursement.RFView;

public class GetOtherFormsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5076656763328481515L;
	
	public GetOtherFormsServlet(){
		super();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		int userID = (Integer) request.getSession().getAttribute("employeeId");
		
		ApproverDao getInfo = new ApproverDao();
		
		LinkedList<Integer> rfIDList;
		
		try {
			rfIDList = getInfo.fetchForms(userID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			rfIDList = new LinkedList<Integer>();
			e.printStackTrace();
		}
		
		RFViewRow[] rowArray = new RFViewRow[rfIDList.size()];
		
		for(int i = 0; i < rfIDList.size(); i++){
			
			try {
				rowArray[i] = getInfo.getRFInfo(rfIDList.get(i));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				rowArray[i] = null;
				e.printStackTrace();
			}
		}
		
		PrintWriter pw = response.getWriter();
		
		if(rowArray.length == 0){
			pw.print("");
		}else{
			RFView rfv = new RFView(rowArray);
			response.setContentType("application/json");

			String rfOwnListString = (new ObjectMapper()).writeValueAsString(rfv);

			pw.print(rfOwnListString);
		}
	}
	
	
}
