package dao;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.LinkedList;

//import jsonString.JSONConvert;
import util.ConnectionFactory;

public abstract class ApproverDao {
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
	
	public abstract boolean denyReimbursement(int rfId) throws SQLException;
	
	public String[] getRFInfo(int rfId) throws SQLException {
		String sql = "select * from Reimburse_Form where status = ?";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, rfId);
		ResultSet rs = stmt.executeQuery();
		
		String[] returnArray = new String[rs.getFetchSize()];
		int i = 0;
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
			
//			returnArray[i] = JSONConvert.getJSON(strArray);
		}
		return returnArray;
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
