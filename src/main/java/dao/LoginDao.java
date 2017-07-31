package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnectionFactory;

public class LoginDao {
	Connection conn;
	
	public LoginDao() {}
	
	public int loginAndGetId(String username, String password) throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		
		String sql = "select e_id from Employee where username = ? and password = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, username);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();
		int employeeId = 0;
		
		if (rs.next()) {
			employeeId = rs.getInt(1);
		}
		
		conn.close();
		return employeeId;
	}
	
}
