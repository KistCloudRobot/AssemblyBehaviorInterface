package kr.ac.uos.ai.behavior.communication.message.serial.request;

import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public class CheckTray extends SerialMessage {

	public CheckTray(String sender, String actionID) {
		super(sender, ActionType.CheckTray, actionID);
	}

	@Override
	public String makeResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
