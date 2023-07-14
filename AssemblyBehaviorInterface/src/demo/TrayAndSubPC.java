package demo;

import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.Configuration;
import kr.ac.uos.ai.behavior.TrayBehaviorInterface;

public class TrayAndSubPC {

	public static void main(String[] args) {
		String brokerAddress = Configuration.SERVER_ADDRESS;
		int brokerPort = Configuration.SERVER_PORT_TRAY;
		String trayPort = Configuration.TRAY_PORT;
		String subPCPort = Configuration.SUB_PC_PORT;
		
		BehaviorInterface bi = new TrayBehaviorInterface(brokerAddress,brokerPort ,trayPort , subPCPort);
		ArbiAgentExecutor.execute(brokerAddress, brokerPort, Configuration.BEHAVIOR_INTERFACE_ADDRESS, bi, Configuration.BROKER_TYPE);
	}
}
