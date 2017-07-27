package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

import util.ConnectionFactory;

public class RequestorDao {
	Connection conn;
	int rfId;
	
	public RequestorDao(int rfId) {
		this.rfId = rfId;
	}
	
	public boolean cancelReimbursement(int rfId) throws SQLException{
		conn = ConnectionFactory.getInstance().getConnection();
		conn.setAutoCommit(false);
		String sql = "update Reimbursement_Form set status = 0 where status = 4 and rf_id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rfId);
		int count = stmt.executeUpdate();
		Savepoint s = conn.setSavepoint();
		conn.close();
		
		if (count != 1) {
			conn.rollback(s);
		}
		
		return count != 1;
	}
	
	
	public boolean submitReimbursement(	int eId, Date startDate, Date endDate,
										Date startTime, Date endTime, 
										String location, String description, 
										double cost, String gradingFormat,
										String eventType, String workRelated) throws SQLException{
		
		conn = ConnectionFactory.getInstance().getConnection();
		conn.setAutoCommit(false);
		String sql = "insert into Reimbursement_Form ("
				+ "e_id, start_date, end_date, "
				+ "start_time, end_time, location, "
				+ "description, cost, grading_format, "
				+ "event_type, work_related) values (?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, eId);
		stmt.setDate(2, startDate);
		stmt.setDate(3, endDate);
		stmt.setDate(4, startTime);
		stmt.setDate(5, endTime);
		stmt.setString(6, location);
		stmt.setString(7, description);
		stmt.setDouble(8, cost);
		stmt.setString(9, gradingFormat);
		stmt.setString(10, eventType);
		stmt.setString(11, workRelated);
		
		Savepoint s = conn.setSavepoint();
		int count = stmt.executeUpdate();
		
		if (count!=1) {
			conn.rollback(s);
		}
		conn.close();
		return count != 1;
			
	}
}
