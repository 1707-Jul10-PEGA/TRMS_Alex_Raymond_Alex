package model.reimbursement;

public class RFView {
	RFViewRow[] rfVRows;
	
	public RFView(RFViewRow[] rfVRow) {
		this.rfVRows = rfVRow;
	}

	public RFViewRow[] getRfVRows() {
		return rfVRows;
	}

	public void setRfVRows(RFViewRow[] rfVRows) {
		this.rfVRows = rfVRows;
	}
	
}
