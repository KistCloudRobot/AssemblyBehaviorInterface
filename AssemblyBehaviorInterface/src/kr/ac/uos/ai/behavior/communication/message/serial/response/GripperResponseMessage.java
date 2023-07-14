package kr.ac.uos.ai.behavior.communication.message.serial.response;

public class GripperResponseMessage extends SerialResponseMessage {
	
	private final String[] response;
	
	public GripperResponseMessage(String[] response) {
		this.response = response;
	}
	
	public String[] getResponse() {
		return response;
	}
	
}
