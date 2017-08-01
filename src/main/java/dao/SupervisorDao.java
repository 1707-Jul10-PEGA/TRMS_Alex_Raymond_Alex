package dao;

import java.sql.SQLException;

public class SupervisorDao extends ApproverDao {

	@Override
	public boolean denyReimbursement(int rfId) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
