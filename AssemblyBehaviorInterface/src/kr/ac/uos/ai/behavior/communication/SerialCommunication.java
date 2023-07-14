package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.adaptor.SerialAdaptor;

public abstract class SerialCommunication extends Communication{
	
	public SerialCommunication(BehaviorInterface bi, String portName) {
		super(bi);
		this.adaptor = new SerialAdaptor(this, portName);
	}

}
