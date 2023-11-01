package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.adaptor.Adaptor;

public abstract class Communication {

	protected Adaptor adaptor;
	protected BehaviorInterface behaviorInterface;
	
	public Communication(BehaviorInterface bi) {
		this.behaviorInterface = bi;
	}
	
	public abstract void onMessage(String message);
	
	public void connect() {
		adaptor.connect();
	}
	
	public void send(String message) {
		adaptor.send(message);
	}
	
	
	protected String removeEndLineMarker(String input) {
		input = input.replace("\r\n", "");
		return input;
	}
}
