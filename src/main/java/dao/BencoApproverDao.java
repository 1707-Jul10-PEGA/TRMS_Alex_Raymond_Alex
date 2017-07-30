package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

import util.ConnectionFactory;

public class BencoApproverDao extends ApproverDao {
	BencoApproverDao(int eId) {
		super(eId);
	}
	
	public boolean changeAmount(double amount) throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		conn.setAutoCommit(false);
		String sql = "update Reimbursement_form set cost = ? where rfId = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setDouble(1, amount);
		Savepoint s = conn.setSavepoint();
		int count = stmt.executeUpdate();
		
		
		if (count!=1) {
			conn.rollback(s);
		}
		conn.setAutoCommit(true);
		conn.close();
		return count != 1;
		
	}
}
