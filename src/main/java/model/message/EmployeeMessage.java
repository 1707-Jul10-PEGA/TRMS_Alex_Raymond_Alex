package model.message;

public class EmployeeMessage {
	int eMsgId;
	int rfId;
	int sender;
	int receiver;
	String message;
	String messageType;
	String timeSent;

	public EmployeeMessage() {
		super();
	}

	public int geteMsgId() {
		return eMsgId;
	}

	public void seteMsgId(int eMsgId) {
		this.eMsgId = eMsgId;
	}

	public int getRfId() {
		return rfId;
	}

	public void setRfId(int rfId) {
		this.rfId = rfId;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getTimeSent() {
		return timeSent;
	}

	public void setTimeSent(String timeSent) {
		this.timeSent = timeSent;
	}
	
}
