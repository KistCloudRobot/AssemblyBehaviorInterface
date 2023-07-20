package kr.ac.uos.ai.behavior.communication.message.serial.response;

public class GripperInitResponseMessage extends SerialResponseMessage {
	
	private final String response;
	
	public GripperInitResponseMessage(String response) {
		this.response = response;
	}
	
	public String getResponse() {
		return response;
	}
	
}
