package dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.LinkedList;

import model.reimbursement.RFViewRow;
import model.reimbursement.ReimbursementForm;
//import jsonString.JSONConvert;
import util.ConnectionFactory;

public class ApproverDao {
	Connection conn;

	public ApproverDao() {
		super();
		connect();
	}
	
	private void connect(){
		conn = ConnectionFactory.getInstance().getConnection();
	}
	
	public LinkedList<Integer> fetchForms(int userID) throws SQLException {
		
		String dept = "";
		boolean benCo = false;
		boolean deptHead = false;
		
		LinkedList<Integer> output = new LinkedList<Integer>();
		
		String sql = "select department, department_head from Employee where e_id = ?";
		
		PreparedStatement pstmnt = conn.prepareStatement(sql);
		pstmnt.setInt(1, userID);
		
		ResultSet user = pstmnt.executeQuery();
		
		while(user.next()){
			dept = user.getString(1);
			benCo = dept.equals("BC");
			deptHead = user.getBoolean(2);
		}
		
		String dsSql = "select Reimbursement_Form.rf_id FROM "
				+ "Reimbursement_Form Inner Join Employee "
				+ "ON Reimbursement_Form.e_id = Employee.e_id WHERE "
				+ "Employee.supervisor = ? AND Reimbursement_Form.status = 1";
		
		PreparedStatement getSupervised = conn.prepareStatement(dsSql);
		
		getSupervised.setInt(1, userID);
		
		ResultSet supervised = getSupervised.executeQuery();
		
		while(supervised.next()){
			Integer rfId = supervised.getInt(1);
			if(!output.contains(rfId)){
				output.add(rfId);
			}
		}
		
		
		if(deptHead){
			String dhSql = "select Reimbursement_Form.rf_id FROM "
					+ "Reimbursement_Form Inner Join Employee "
					+ "ON Reimbursement_Form.e_id = Employee.e_id WHERE "
					+ "Employee.department = ? AND Reimbursement_Form.status = 2";
			
			PreparedStatement deptFormIDs = conn.prepareStatement(dhSql);
			
			deptFormIDs.setString(1, dept);
			
			ResultSet deptForms = deptFormIDs.executeQuery();
			
			while(deptForms.next()){
				Integer rfId = deptForms.getInt(1);
				if(!output.contains(rfId)){
					output.add(rfId);
				}
			}
		}
		
		if(benCo){
			
			String bcSql = "select Reimbursement_Form.rf_id, "
					+ " Employee.department FROM Reimbursement_Form Inner Join"
					+ " Employee ON Reimbursement_Form.e_id = Employee.e_id "
					+ "WHERE (Reimbursement_Form.benCo = ? OR Reimbursement_Form.benCo = ?) "
					+ "AND Reimbursement_Form.status = 3 "
					+ "AND Employee.supervisor <> ?";
			
			PreparedStatement benCoForms = conn.prepareStatement(bcSql);
			
			benCoForms.setInt(1, userID);
			benCoForms.setString( 2, null);
			benCoForms.setInt(3, userID);
			
			ResultSet bcForms = benCoForms.executeQuery();
			
			while(bcForms.next()){
				
				int rfId = bcForms.getInt(1);
				String empDept = bcForms.getString(2);
				
				if (!("BC".equals(empDept) && deptHead )){
					if (!output.contains(rfId)){
						output.add(rfId);
					}
				}
				
			}
			
		}
		
		return output;
	}
	
	public boolean approveReimbursement(int rfId) throws SQLException{
		
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
	
	public boolean denyReimbursement(int rfId) throws SQLException{
		
		conn = ConnectionFactory.getInstance().getConnection();
		conn.setAutoCommit(false);
		conn.setSavepoint();
		
		String sql = "update Reimbursement_Form set status=0 Where rf_id=?";
		
		PreparedStatement pstmnt = conn.prepareStatement(sql);
		
		pstmnt.setInt(1, rfId);
		
		int count = pstmnt.executeUpdate();
		
		if(count != 1){
			conn.rollback();
		}
		
		conn.setAutoCommit(true);
		conn.close();
		return (count == 1);
	}
	
	public RFViewRow getRFInfo(int rfId) throws SQLException {
		conn = ConnectionFactory.getInstance().getConnection();
		String sql = "select Employee.first, Employee.last "
				+ "FROM Reimbursement_Form INNER JOIN Employee "
				+ "ON Reimbursement_Form.e_id = Employee.e_id "
				+ "WHERE Reimbursement_Form.rf_id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rfId);
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
			String first = rs.getString(1);
			String last = rs.getString(2);
			RFViewRow rf = new RFViewRow(rfId, first, last);
			return rf;
		} else {
			return null;
		}
	}
	
	public boolean requestForInfo(String detail, int receiver, int rfId, int eId) throws SQLException {
		
		String sql = "select * from Request where rf_id = ? and sender = ? and receiver = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rfId);
		stmt.setInt(2, eId);
		stmt.setInt(3, receiver);
		
		ResultSet rs = stmt.executeQuery();
		
		int count;
		if (!rs.next()){
			rs.close();
			stmt.close();
			
			sql = "insert into Request(rf_id, sender, receiver, detail) values (?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, rfId);
			stmt.setInt(2, eId);
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