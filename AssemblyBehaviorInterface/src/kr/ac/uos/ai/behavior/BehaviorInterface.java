package kr.ac.uos.ai.behavior;

import kr.ac.uos.ai.arbi.agent.ArbiAgent;
import kr.ac.uos.ai.arbi.ltm.DataSource;
import kr.ac.uos.ai.behavior.communication.message.BehaviorMessage;
import kr.ac.uos.ai.behavior.log.BehaviorLogger;

public abstract class BehaviorInterface extends ArbiAgent{

	protected BehaviorLogger logger;
	protected DataSource dataSource;
	
	public BehaviorInterface(String brokerAddress, int brokerPort) {
		logger = BehaviorLogger.getLogger();
		dataSource = new DataSource();
		dataSource.connect(brokerAddress, brokerPort, Configuration.BEHAVIOR_INTERFACE_DATA_SOURCE_ADDRESS, Configuration.BROKER_TYPE);
	}
	
	public void sendMessage(String receiver, String message) {
		System.out.println("[send]\t\t: " + receiver + " --> " + message + " timestamp :" +System.currentTimeMillis());
		this.send(receiver, message);
	}
	
	public void onMessage(BehaviorMessage message) {
		logger.log("wrong message arrived to bi onMessage : " + message.toString());
	}
	
}
