package kr.ac.uos.ai.behavior.communication.message.robot.request;

import kr.ac.uos.ai.behavior.communication.message.robot.RobotMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;
import kr.ac.uos.ai.behavior.communication.message.value.RobotCommand;

public class MoveToTray extends RobotMessage{

	private int x;
	private int y;

	public MoveToTray(String sender, String actionID, String position, int x, int y) {
		this(sender, actionID, RobotCommand.valueOf(position), x, y);
	}

	private MoveToTray(String sender, String actionID, RobotCommand command, int x, int y) {
		super(sender, ActionType.MoveToTray, actionID, command);
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public String getMessage() {
		String result = command.getValue() + "," + x + "," + y +",0,0,0";
		return result;
	}
}
