package kr.ac.uos.ai.behavior;

import kr.ac.uos.ai.arbi.BrokerType;

public class Configuration {

	public static final String BEHAVIOR_INTERFACE_ADDRESS = "agent://www.arbi.com/BehaviorInterface";
	public static final String BEHAVIOR_INTERFACE_DATA_SOURCE_ADDRESS = "ds://www.arbi.com/BehaviorInterface";
	
	public static final String SERVER_ADDRESS = "127.0.0.1";
	public static final int SERVER_PORT_EPSON = 61116;
	public static final int SERVER_PORT_UR = 61115;
	public static final int SERVER_PORT_TRAY = 61114;
	public static final BrokerType BROKER_TYPE = BrokerType.ACTIVEMQ;
	
	public static final String EPSON_ARM_ADDRESS = "127.0.0.1";
	public static final int EPSON_ARM_PORT = 1470;
	public static final String EPSON_GRIPPER_PORT = "COM2";
	
	public static final String UR_ARM_ADDRESS = "192.168.10.30";
	public static final int UR_ARM_PORT = 29999;
	public static final String UR_GRIPPER_PORT = "COM12";
	
	public static final String TRAY_PORT = "COM??";
	public static final String SUB_PC_PORT = "COM??";
	
	public static final int BEATRATE = 115200;
	
}
