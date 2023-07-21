package kr.ac.uos.ai.behavior.communication.message.serial.response;

public class GripperStatusMessage extends SerialResponseMessage {
	
	private final String[] response;
	
	public GripperStatusMessage(String[] response) {
		this.response = response;
	}
	
	public String[] getResponse() {
		return response;
	}
	
}
