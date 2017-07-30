package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

import util.ConnectionFactory;

public abstract class ApproverDao {
Connection conn;
int eId;

	public ApproverDao(int eId) {
		this.eId = eId;
	}
	
	public boolean approveReimbursement(int rfId) throws SQLException{
		conn = ConnectionFactory.getInstance().getConnection();
		conn.setAutoCommit(false);
		String sql = "update Reimbursement_Form set status = status + 1 where rf_id = ?";
			
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rfId);
		Savepoint s = conn.setSavepoint();
		int count = stmt.executeUpdate();
		
		if (count != 1) {
			conn.rollback(s);
		}
		conn.setAutoCommit(true);
		
		conn.close();
		return 	count != 1;
	}
	
	public boolean denyReimbursement(int rfId) throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		conn.setAutoCommit(false);
		String sql = "update Reimbursement_Form set status = 0 where rf_Id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rfId);
		Savepoint s = conn.setSavepoint();
		int count = stmt.executeUpdate();
		
		if (count!=1) {
			conn.rollback(s);
		}
		conn.setAutoCommit(true);
		
		conn.close();
		return count != 1;
	}
	
	public String[] getRFInfo(int rfStatus) throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		String sql = "select * from Reimburse_Form where status = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rfStatus);
		ResultSet rs = stmt.executeQuery();
		
		String[] returnArray = new String[rs.getFetchSize()];
		
		while (rs.next()){
			
			String[] strArray = new String[13];
			strArray[0] = String.valueOf(rs.getInt(1));	 	// rf_id
			strArray[1] = String.valueOf(rs.getInt(2)); 	// e_id
			strArray[2] = String.valueOf(rs.getDate(3));	// start_date
			strArray[3] = String.valueOf(rs.getDate(4));	// start_time
			strArray[4] = String.valueOf(rs.getDate(5));	// end_time
			strArray[5] = rs.getString(6);					// location
			strArray[6] = rs.getString(7);					// description
			strArray[7] = String.valueOf(rs.getInt(8)); 	// cost
			strArray[8] = rs.getString(9);					// grading_format
			strArray[9] = rs.getString(10);					// event_type
			strArray[10] = rs.getString(11);				// work_related
			strArray[11] = String.valueOf(rs.getInt(13));	// status
			strArray[12] = String.valueOf(rs.getDate(14));	// last activity
			//NOTE: blob object: approval object not included
			
			
		}
		return returnArray;
	}
	
	public boolean requestForInfo(int rfId, int receiver, String detail) throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		String sql = "select * from Request where rf_id = ? and sender = ? and receiver = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rfId);
		stmt.setInt(2, this.eId);
		stmt.setInt(3, receiver);
		
		ResultSet rs = stmt.executeQuery();
		
		int count;
		if (!rs.next()){
			rs.close();
			stmt.close();
			
			sql = "insert into Request(rf_id, sender, receiver, detail) values (?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, rfId);
			stmt.setInt(2, this.eId);
			stmt.setInt(3, receiver);
			stmt.setString(4, detail);
			
			Savepoint s = conn.setSavepoint();
			count = stmt.executeUpdate();
			
			if (count != 1) {
				conn.rollback(s);
			}
		}
		else {
			rs.close();
			stmt.close();
			
			sql = "update Request set detail = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, detail);
			
			Savepoint s = conn.setSavepoint();
			count = stmt.executeUpdate();
			
			if (count != 1) {
				conn.rollback(s);
			}
		}
		conn.close();
		return count == 1;
	}
}
