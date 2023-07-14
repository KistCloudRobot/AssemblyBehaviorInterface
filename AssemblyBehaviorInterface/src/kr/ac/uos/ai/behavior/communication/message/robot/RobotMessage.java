package kr.ac.uos.ai.behavior.communication.message.robot;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.behavior.communication.message.BehaviorMessage;
import kr.ac.uos.ai.behavior.communication.message.ResponseLock;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckEndMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;
import kr.ac.uos.ai.behavior.communication.message.value.RobotCommand;

public abstract class RobotMessage extends BehaviorMessage{

	private String sender;
	private String actionID;
	protected RobotCommand command;
	protected AckMessage responseMessage;
	protected final ActionType actionType;
	
	private final ResponseLock responseLock;
	
	public RobotMessage(String sender, ActionType actionType, String actionID, RobotCommand command) {
		this.sender = sender;
		this.actionType = actionType;
		this.actionID = actionID;
		this.command = command;
		responseLock = new ResponseLock();
	}

	public void setResponse(AckMessage responseMessage) {
		responseLock.lock();
		try {
			this.responseMessage = responseMessage;
			responseLock.signal();
		} finally {
			responseLock.unlock();
		}
		
	}
	
	public String makeResponse() {
		String response = "(fail)";
		if (this.responseMessage instanceof AckEndMessage) {
			Expression actionID = GLFactory.newExpression(GLFactory.newValue(this.getActionID()));
			Expression actionResult;
			int result = ((AckEndMessage) this.responseMessage).getType();
			if (result == 3) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else {
				actionResult = GLFactory.newExpression(GLFactory.newValue("fail"));
			}
			GeneralizedList gl = GLFactory.newGL("ActionResult", actionID, actionResult);
			response = gl.toString();
		} else if (this.responseMessage instanceof AckMessage) {
			response = "(ok)";
		}
		return response;
	}
	
	public String getResponse() {
		responseLock.lock();
		String response = null;
		try {
			do {
				if(this.responseMessage == null) {
					responseLock.await();
				}
				if (this.responseMessage != null) {
					response = this.makeResponse();
					if(response == null) {
						this.responseMessage = null;
						continue;
					}
				}
			} while(responseMessage == null);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			responseLock.unlock();
		}
		this.responseMessage = null;
		return response;
	}
	
	public String getSender() {
		return sender;
	}
	public String getActionID() {
		return actionID;
	}
	public RobotCommand getCommand() {
		return command;
	}
	public ActionType getActionType() {
		return actionType;
	}
	
	public abstract String getMessage();
	
}
