package model.employee;

import java.util.HashMap;

public class Approver extends Employee{
	HashMap<Integer, Employee> workers;
	
	Approver(String firstName, String lastName, String department, HashMap<Integer, Employee> workers) {
		super(firstName, lastName, department);
	}
}
