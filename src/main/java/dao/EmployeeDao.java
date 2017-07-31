package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.reimbursement.RFViewRow;
import util.ConnectionFactory;

public class EmployeeDao {
	Connection conn;
	
	public EmployeeDao() {}
	
	public RFViewRow[] getOwnForms(int eId) throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		
		String sql = "select rf.rf_id, e.first, e.last from Reimbursement_Form rf, Employee e where e.e_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, eId);
		ResultSet rs = stmt.executeQuery();
		
		RFViewRow[] forms = new RFViewRow[rs.getFetchSize()];
		
		int i = 0;
		while (rs.next()) {
			
			forms[i] = new RFViewRow(rs.getInt(1), rs.getString(2), rs.getString(3));
			i += 1;
		}
		if (forms[0] == null) {
			return null;
		}
		conn.close();
		
		return forms;
	}
}
