package kr.ac.uos.ai.behavior.communication.message.robot.acknowledge;

import kr.ac.uos.ai.behavior.communication.message.value.RobotID;
import kr.ac.uos.ai.behavior.communication.message.value.RobotPosition;
import kr.ac.uos.ai.behavior.communication.message.value.RobotState;

public class RobotStatusMessage extends AckMessage {

	private final RobotState state;
	private final RobotPosition	position; 
	private final RobotID robotID;
	
	public RobotStatusMessage(int command, int position ,int status, int robotID) {
		super(command, 0);
		this.position = RobotPosition.getEnum(position);
		this.state = RobotState.getEnum(status);
		this.robotID = RobotID.getEnum(robotID);
	}

	public RobotPosition getPosition() {
		return position;
	}
	public RobotState getState() {
		return state;
	}
	public RobotID getRobotID() {
		return robotID;
	}
	
}
