package demo;

import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.Configuration;
import kr.ac.uos.ai.behavior.EpsonBehaviorInterface;
import kr.ac.uos.ai.behavior.URBehaviorInterface;

public class Epson {

	public static void main(String[] args) {
		String brokerAddress = Configuration.SERVER_ADDRESS;
		int brokerPort = Configuration.SERVER_PORT_EPSON;
		int epsonPort = Configuration.EPSON_ARM_PORT;
		String epsonGripper = Configuration.EPSON_GRIPPER_PORT;
		
		BehaviorInterface bi = new EpsonBehaviorInterface(brokerAddress, brokerPort, "Epson", epsonPort, epsonGripper);
		
		ArbiAgentExecutor.execute(brokerAddress, brokerPort, Configuration.BEHAVIOR_INTERFACE_ADDRESS, bi, Configuration.BROKER_TYPE);
		
	}
}
