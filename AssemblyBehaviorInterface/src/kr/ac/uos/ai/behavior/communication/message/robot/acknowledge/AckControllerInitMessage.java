package kr.ac.uos.ai.behavior.communication.message.robot.acknowledge;

import kr.ac.uos.ai.behavior.communication.message.BehaviorMessage;
import kr.ac.uos.ai.behavior.communication.message.value.RobotCommand;

public class AckControllerInitMessage extends AckMessage{

	private String response;
	
	public AckControllerInitMessage(String response) {
		super(0, 0);
		this.response = response;
	}

	public String getResponse() {
		return response;
	}
	
}
