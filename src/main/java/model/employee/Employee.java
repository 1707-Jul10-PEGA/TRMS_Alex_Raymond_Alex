package model.employee;

public class Employee {
	int employeeId;
	String firstName;
	String lastName;
	String department;
	boolean isDH;


	public Employee(String firstName, String lastName, String department) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
	}


	// this code will be on servlet
	// Success 1, Fail 0, Need to provide a reason 2, Invalid input 3
	public int processReimbursement (int r_id, String role, String response, String ...reason)  {
		
		if ("DENY".equalsIgnoreCase(response)) {
			// If denied without a reason, return and ask user to do so
			if (reason == null) return 2;
			
			// If denied, update database with new status 'denied' and comment for reason
		}
		else if ("APPROVE".equals(response)) {
			// else base on the provided variable 'role', update the corresponding column with 'true'
		}
		else {
			//Invalid input
			return 3;
		}
		// return status of update 
		return 1;
	}
	
	//
}
