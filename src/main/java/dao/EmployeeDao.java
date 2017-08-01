package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.reimbursement.RFViewRow;
import model.reimbursement.ReimbursementForm;
import util.ConnectionFactory;

public class EmployeeDao {
	Connection conn;

	public EmployeeDao() {
	}

	private int getResultSize(String sql, int eId) throws SQLException{
		conn = ConnectionFactory.getInstance().getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, eId);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		
		int size = rs.getInt(1);
		conn.close();
		
		return size;
	}

	public RFViewRow[] getOwnForms(int eId) throws SQLException {
		String sizeSql = "select count(rf.rf_id) from Reimbursement_Form rf, Employee e where rf.e_id = e.e_id and e.e_id = ?";
		int size = this.getResultSize(sizeSql, eId);

		if (size == 0) {
			return null;
		}
		
		conn = ConnectionFactory.getInstance().getConnection();
		String sql = "select rf.rf_id, e.first, e.last from Reimbursement_Form rf, Employee e where rf.e_id = e.e_id and e.e_id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, eId);
		ResultSet rs = stmt.executeQuery();

		RFViewRow[] forms = new RFViewRow[size];

		int i = 0;
		while (rs.next()) {

			forms[i] = new RFViewRow(rs.getInt(1), rs.getString(2), rs.getString(3));
			System.out.println(forms[i].getFirst());
			System.out.println(forms[i].getLast());
			System.out.println(forms[i].getRfId());
			i += 1;
		}
		if (forms[0] == null) {
			System.out.println();
			return null;
		}
		conn.close();

		return forms;
	}
	
	
	public ReimbursementForm getRFInfo(int rfId) throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		String sql = "select * from Reimbursement_Form where rf_id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rfId);
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			ReimbursementForm rf = new ReimbursementForm();
			rf.seteId(rs.getInt(2)); 							// e_id
			rf.setStartDate(rs.getDate(3));						// start_date
			rf.setStartTime(rs.getDate(4));						// start_time
			rf.setEndTime(rs.getDate(5));						// end_time
			rf.setLocation(rs.getString(6));					// location
			rf.setDescription(rs.getString(7));					// description
			rf.setCost(rs.getDouble(8)); 						// cost
			rf.setAmount(rs.getDouble(9));						// amount
			rf.setGradingFormat(rs.getString(10));				// grading_format
			rf.setEventType(rs.getString(11));					// event_type
			rf.setWorkRelated(rs.getString(12));				// work_related
			rf.setStatus(rs.getInt(14));							// status
			rf.setLastActivity(rs.getDate(15));					// last activity
			//NOTE: blob object: approval object not included
			return rf;
		} else {
			return null;
		}
	}
}
