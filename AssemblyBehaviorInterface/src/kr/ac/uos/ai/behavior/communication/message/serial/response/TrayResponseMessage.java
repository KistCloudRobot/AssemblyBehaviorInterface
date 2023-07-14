package kr.ac.uos.ai.behavior.communication.message.serial.response;

public class TrayResponseMessage extends SerialResponseMessage {
	
	private final String response;
	
	public TrayResponseMessage(String response) {
		this.response = response;
	}
	
	public String getResponse() {
		return response;
	}
	
}
