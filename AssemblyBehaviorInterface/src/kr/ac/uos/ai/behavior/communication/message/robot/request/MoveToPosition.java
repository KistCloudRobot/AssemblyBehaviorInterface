package kr.ac.uos.ai.behavior.communication.message.robot.request;

import kr.ac.uos.ai.behavior.communication.message.robot.RobotMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;
import kr.ac.uos.ai.behavior.communication.message.value.RobotCommand;

public class MoveToPosition extends RobotMessage{
	
	public MoveToPosition(String sender, String actionID, String position) {
		this(sender, actionID, RobotCommand.valueOf(position));
	}
	
	private MoveToPosition(String sender, String actionID, RobotCommand command) {
		super(sender, ActionType.MoveToPosition, actionID, command);
	}

	@Override
	public String getMessage() {		String result = "";
		if (command.getValue() == 11 || command.getValue() == 13 || command.getValue() == 16 || command.getValue() == 21 || command.getValue() == 26) {
			result = command.getValue() + ",0,0,0";
		} else {
			result = command.getValue() + ",0,0,0,0,0";
		}
		return result;
	}
	
}
