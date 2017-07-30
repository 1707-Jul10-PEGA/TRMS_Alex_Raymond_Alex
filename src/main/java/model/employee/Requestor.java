package model.employee;

import java.util.ArrayList;

public class Requestor extends Employee{
	ArrayList<Integer> rfs; //hold references to all reimbursement forms
	int directSupervisorId;

	
	Requestor(String firstName, String lastName, ArrayList<Integer> rfs, int directSupervisorId, String department) {
		super(firstName, lastName, department);
	}


	public ArrayList<Integer> getRfs() {
		return rfs;
	}


	public void setRfs(ArrayList<Integer> rfs) {
		this.rfs = rfs;
	}

	public void addRf(int rfId) {
		this.rfs.add(rfId);
	}
	

	public int getDirectSupervisorId() {
		return directSupervisorId;
	}

}
