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
	
	public boolean checkAuthority(int rfId, int eId) throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		String sql = "select rf.rf_id, e.e_id,e.department, e.department_head from Reimbursement_Form rf, Employee e" +
				" where rf.rf_id = ? and rf.e_id = e.e_id and not (rf.e_id = ?) and ( e.supervisor = ? and (rf.status = 1 or rf.status = 6)) or (((" +
				" select e.department from Reimbursement_Form rf, Employee e where e.e_id = rf.e_id) = (select e.department from Reimbursement_Form rf, Employee e where e.e_id = ?)) and ((" +
				" select e.department_head from Reimbursement_Form rf, Employee e where e.e_id = rf.e_id) = 1) and (rf.status = 2 or rf.status = 7)) or ((((" + 
				" select e.department from Reimbursement_Form rf, Employee e where e.e_id = rf.e_id) = 'BC') and not ((select e.supervisor from Reimbursement_Form rf, Employee e where e.e_id = rf.rf_id) = ?)" + 
				" and not ((select e_id from Employee where department_head = 1) = ?) and (rf.status = 3 or rf.status = 8)))";
				//
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rfId);
		stmt.setInt(2, eId);
		stmt.setInt(3, eId);
		stmt.setInt(4, eId);
		stmt.setInt(5, eId);
		stmt.setInt(6, eId);
		ResultSet rs = stmt.executeQuery();
		int approver = 0;
		
		if (rs.next()) {
			approver = rs.getInt(1);
		}
		conn.close();
		
		return approver == 1;
	}
	
	public ReimbursementForm getRFInfo(int rfId, int eId) throws SQLException {
		boolean approver = checkAuthority(rfId, eId);
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
			rf.setStatus(rs.getInt(14));						// status
			rf.setLastActivity(rs.getDate(15));					// last activity
			rf.setApprover(approver);
			//NOTE: blob object: approval object not included
			
			rs.close();
			return rf;
		} else {
			return null;
		}
	}
}
