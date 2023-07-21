package kr.ac.uos.ai.behavior.communication.message.serial.response;

public class SubPCResponseMessage extends SerialResponseMessage {
	
	private final String response;
	
	public SubPCResponseMessage(String response) {
		this.response = response;
	}
	
	public String getResponse() {
		return response;
	}
	
}
