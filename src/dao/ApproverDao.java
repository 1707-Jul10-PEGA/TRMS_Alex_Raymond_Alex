package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

import util.ConnectionFactory;

public class ApproverDao {
Connection conn;
int rfId;

	public ApproverDao(int rfId) {
		this.rfId = rfId;
	}
	
	public boolean approveReimbursement(int status) throws SQLException{
		conn = ConnectionFactory.getInstance().getConnection();
		conn.setAutoCommit(false);
		String sql = "update Reimbursement_Form set status = ? where rf_id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, status);
		stmt.setInt(2, rfId);
		int count = stmt.executeUpdate();
		Savepoint s = conn.setSavepoint();
		
		if (count != 1) {
			conn.rollback(s);
		}
		conn.setAutoCommit(true);
		
		conn.close();
		return 	count != 1;
	}
	
	public boolean denyReimbursement() throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		conn.setAutoCommit(false);
		String sql = "update Reimbursement_Form set status = 0 where rf_Id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rfId);
		int count = stmt.executeUpdate();
		Savepoint s = conn.setSavepoint();
		
		if (count!=1) {
			conn.rollback(s);
		}
		conn.setAutoCommit(true);
		
		conn.close();
		return count != 1;
	}
	
	public boolean changeAmount(double amount) throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		conn.setAutoCommit(false);
		String sql = "update Reimbursement_form set cost = ? where rfId = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setDouble(1, amount);
		int count = stmt.executeUpdate();
		Savepoint s = conn.setSavepoint();
		
		if (count!=1) {
			conn.rollback(s);
		}
		conn.setAutoCommit(true);
		conn.close();
		return count != 1;
		
	}
}
