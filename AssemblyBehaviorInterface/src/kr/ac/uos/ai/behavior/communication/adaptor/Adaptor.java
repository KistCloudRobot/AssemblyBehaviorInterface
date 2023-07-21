package kr.ac.uos.ai.behavior.communication.adaptor;

import kr.ac.uos.ai.behavior.communication.Communication;
import kr.ac.uos.ai.behavior.log.BehaviorLogger;

public abstract class Adaptor extends Thread {

	protected BehaviorLogger logger;
	protected Communication communication;
	
	public Adaptor(Communication comm) {
		logger = BehaviorLogger.getLogger();
		this.communication = comm;
	}
	
	public abstract void connect();
	
	public void send(String message) {
		
	}
}
