package kr.ac.uos.ai.behavior.communication.message.serial.response;

public class GripperAckMessage extends SerialResponseMessage {
	
	private final String response;
	
	public GripperAckMessage(String response) {
		this.response = response;
	}
	
	public String getResponse() {
		return response;
	}
	
}
