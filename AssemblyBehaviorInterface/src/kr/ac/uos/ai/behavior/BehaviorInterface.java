package kr.ac.uos.ai.behavior;

import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.ltm.DataSource;
import kr.ac.uos.ai.behavior.communication.message.BehaviorMessage;

public abstract class BehaviorInterface extends ArbiAgent{

	protected DataSource dataSource;
	
	public BehaviorInterface(String brokerAddress, int brokerPort) {
		dataSource = new DataSource();
		dataSource.connect(brokerAddress, brokerPort, Configuration.BEHAVIOR_INTERFACE_DATA_SOURCE_ADDRESS, Configuration.BROKER_TYPE);
	}
	
	@Override
	public void onStart() {
		
	}
	
	public void sendMessage(String receiver, String message) {
		System.out.println("[send]\t\t: " + receiver + " --> " + message + " timestamp :" +System.currentTimeMillis());
		this.send(receiver, message);
	}
	
	public abstract void onMessage(BehaviorMessage message);
	
}
