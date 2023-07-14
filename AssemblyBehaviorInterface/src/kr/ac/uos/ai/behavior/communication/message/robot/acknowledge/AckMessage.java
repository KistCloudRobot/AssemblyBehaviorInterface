package kr.ac.uos.ai.behavior.communication.message.robot.acknowledge;

import kr.ac.uos.ai.behavior.communication.message.BehaviorMessage;
import kr.ac.uos.ai.behavior.communication.message.value.RobotCommand;

public class AckMessage extends BehaviorMessage{

	private final RobotCommand command;
	private final int messageType;
	
	public AckMessage(int command, int type) {
		this.command = RobotCommand.getEnum(command);
		this.messageType = type;
	}
	
	
	public RobotCommand getCommand() {
		return command;
	}
	
	public int getType() {
		return messageType;
	}
}
