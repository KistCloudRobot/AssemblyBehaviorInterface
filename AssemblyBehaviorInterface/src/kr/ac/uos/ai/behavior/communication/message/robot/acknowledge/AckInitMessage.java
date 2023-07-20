package kr.ac.uos.ai.behavior.communication.message.robot.acknowledge;

public class AckInitMessage extends AckMessage{

	private String response;
	
	public AckInitMessage(String response) {
		super(0, 0);
		this.response = response;
	}

	public String getResponse() {
		return response;
	}
	
}
