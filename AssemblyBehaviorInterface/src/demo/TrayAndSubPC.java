package demo;

import kr.ac.uos.ai.arbi.agent.ArbiAgentExecutor;
import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.Configuration;
import kr.ac.uos.ai.behavior.ProductionFacilityBehaviorInterface;

public class TrayAndSubPC {

	public static void main(String[] args) {
		String brokerAddress = Configuration.SERVER_ADDRESS;
		int brokerPort = Configuration.SERVER_PORT_PRODUCTION_FACILITY;
		String printerPort = Configuration.LABEL_PRINTER_PORT;
		String subPCPort = Configuration.SUB_PC_PORT;
		String pcbTrayPort = Configuration.PCB_TRAY_PORT;
		String housingTrayPort = Configuration.HOUSING_TRAY_PORT;
		String lensTrayPort = Configuration.LENS_TRAY_PORT;
		String frontTrayPort = Configuration.FRONT_TRAY_PORT;
		
		BehaviorInterface bi = new ProductionFacilityBehaviorInterface(brokerAddress, brokerPort , printerPort, subPCPort, pcbTrayPort, housingTrayPort, lensTrayPort, frontTrayPort);
		ArbiAgentExecutor.execute(brokerAddress, brokerPort, Configuration.BEHAVIOR_INTERFACE_ADDRESS, bi, Configuration.BROKER_TYPE);
	}
}
