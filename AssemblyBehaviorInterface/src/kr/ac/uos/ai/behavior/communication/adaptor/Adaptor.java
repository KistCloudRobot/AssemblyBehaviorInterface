package kr.ac.uos.ai.behavior.communication.adaptor;

import kr.ac.uos.ai.behavior.communication.Communication;

public abstract class Adaptor extends Thread {

	protected Communication communication;
	
	public Adaptor(Communication comm) {
		this.communication = comm;
	}
	
	public void connect() {};
	
	public void send(String message) {
		
	}
}
