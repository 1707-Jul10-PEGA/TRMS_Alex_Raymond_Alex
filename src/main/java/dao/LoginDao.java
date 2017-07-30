package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnectionFactory;

public class LoginDao {
	Connection conn;
	String username;
	String password;
	
	public LoginDao(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public int loginAndGetId() throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		
		String sql = "select e_id from Employee where username = ? and password = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, this.username);
		stmt.setString(2, this.password);
		ResultSet rs = stmt.executeQuery();
		int employeeId = 0;
		
		if (rs.next()) {
			employeeId = rs.getInt(1);
		}
		
		conn.close();
		return employeeId;
	}
	
}
