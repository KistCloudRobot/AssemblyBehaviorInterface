package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.adaptor.Adaptor;
import kr.ac.uos.ai.behavior.log.BehaviorLogger;

public abstract class Communication {

	protected BehaviorLogger logger;
	protected Adaptor adaptor;
	protected BehaviorInterface behaviorInterface;
	
	public Communication(BehaviorInterface bi) {
		this.behaviorInterface = bi;
		this.logger = BehaviorLogger.getLogger();
	}
	
	public abstract void onMessage(String message);
	
	public void connect() {
		adaptor.connect();
	}
	
	public void send(String message) {
		adaptor.send(message);
	}
	
	
	protected String removeEndLineMarker(String input) {
		input = input.replace("\r", "");
		input = input.replace("\n", "");
		return input;
	}
}
