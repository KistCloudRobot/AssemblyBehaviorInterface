package kr.ac.uos.ai.behavior.communication.message.serial;

import kr.ac.uos.ai.behavior.communication.message.BehaviorMessage;
import kr.ac.uos.ai.behavior.communication.message.ResponseLock;
import kr.ac.uos.ai.behavior.communication.message.serial.response.SerialResponseMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public abstract class SerialMessage extends BehaviorMessage{

	private String sender;
	private String actionID;
	protected SerialResponseMessage responseMessage;
	protected final ActionType actionType;
	
	private final ResponseLock responseLock;
	
	public SerialMessage (String sender, ActionType actionType, String actionID) {
		this.sender = sender;
		this.actionType = actionType;
		this.actionID = actionID;
		responseLock = new ResponseLock();
	}
	

	public void setResponse(SerialResponseMessage responseMessage) {
		responseLock.lock();
		try {
			this.responseMessage = responseMessage;
			responseLock.signal();
		} finally {
			responseLock.unlock();
		}
		
	}
	
	public abstract String makeResponse();
	
	public String getResponse() {
		String response = null;
		response = this.makeResponse();
		this.responseMessage = null;
		return response;
	}
	
	public String getSender() {
		return sender;
	}
	public String getActionID() {
		return actionID;
	}
	public ActionType getActionType() {
		return actionType;
	}
	
	public abstract String getMessage();
}
