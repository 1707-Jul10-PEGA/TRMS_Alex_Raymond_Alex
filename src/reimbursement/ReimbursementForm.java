package reimbursement;

import java.io.File;

import employee.Employee;

public class ReimbursementForm {
	Employee requestor;
	File doc;
	
	
	public ReimbursementForm(Employee requestor, String[] input) {
		this.requestor = requestor;
	}
	
	public void sendRR() {
		// Insert into new record into database with new RR	
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}
	
	
}
