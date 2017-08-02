package model.reimbursement;

import java.sql.Date;

public class ReimbursementForm {
	int rfId;							// rf_id
	int eId; 							// e_id
	String startDate;						// start_date
	String startTime;						// start_time
	Date endTime;						// end_time
	String location;					// location
	String description;					// description
	Double cost; 						// cost
	Double amount;						// amount
	String gradingFormat;				// grading_format
	String eventType;					// event_type
	String workRelated;					// work_related
	int status;							// status
	Date lastActivity;					// lastActivity
	boolean isApprover;					// confirm the right for this viewer to approve/deny the form 

	public int getRfId() {
		return rfId;
	}

	public void setRfId(int rfId) {
		this.rfId = rfId;
	}

	public ReimbursementForm() {
		super();
	}

	public int geteId() {
		return eId;
	}

	public void seteId(int eId) {
		this.eId = eId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(String gradingFormat) {
		this.gradingFormat = gradingFormat;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getWorkRelated() {
		return workRelated;
	}

	public void setWorkRelated(String workRelated) {
		this.workRelated = workRelated;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}
	
	public boolean isApprover() {
		return isApprover;
	}

	public void setApprover(boolean isApprover) {
		this.isApprover = isApprover;
	}
}
