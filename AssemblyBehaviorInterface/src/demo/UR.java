package demo;

import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.Configuration;
import kr.ac.uos.ai.behavior.URBehaviorInterface;

public class UR {

	public static void main(String[] args) {
		String brokerAddress = Configuration.SERVER_ADDRESS;
		int brokerPort = Configuration.SERVER_PORT_UR;
		int urPort = Configuration.UR_ARM_PORT;
		
		String urServerAddress = Configuration.UR_ARM_SERVER_ADDRESS;
		int urServerPort = Configuration.UR_ARM_SERVER_PORT;
		String urGripper = Configuration.UR_GRIPPER_PORT;
		
		BehaviorInterface bi = new URBehaviorInterface(brokerAddress, brokerPort, "UR", urPort, urServerAddress, urServerPort, urGripper);
		
		ArbiAgentExecutor.execute(brokerAddress, brokerPort, Configuration.BEHAVIOR_INTERFACE_ADDRESS, bi, Configuration.BROKER_TYPE);
	}
}
