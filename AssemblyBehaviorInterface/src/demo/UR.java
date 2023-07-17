package demo;

import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.Configuration;
import kr.ac.uos.ai.behavior.RobotBehaviorInterface;

public class UR {

	public static void main(String[] args) {
		String brokerAddress = Configuration.SERVER_ADDRESS;
		int brokerPort = Configuration.SERVER_PORT_UR;
		String epsonAddress = Configuration.UR_ARM_ADDRESS;
		int epsonPort = Configuration.UR_ARM_PORT;
		String epsonGripper = Configuration.UR_GRIPPER_PORT;
		
		BehaviorInterface bi = new RobotBehaviorInterface(brokerAddress, brokerPort, "UR", epsonPort, epsonGripper);
		
		ArbiAgentExecutor.execute(brokerAddress, brokerPort, Configuration.BEHAVIOR_INTERFACE_ADDRESS, bi, Configuration.BROKER_TYPE);
	}
}
