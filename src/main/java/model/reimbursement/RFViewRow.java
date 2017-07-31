package model.reimbursement;

public class RFViewRow {
	int rfId;
	String first;
	String last;
	
	public RFViewRow(int rfId, String first, String last) {
		super();
		this.rfId = rfId;
		this.first = first;
		this.last = last;
	}

	public int getRfId() {
		return rfId;
	}

	public void setRfId(int rfId) {
		this.rfId = rfId;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}
	

}
