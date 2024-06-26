package kr.ac.uos.ai.behavior;

import kr.ac.uos.ai.arbi.BrokerType;

public class Configuration {

	public static final String BEHAVIOR_INTERFACE_ADDRESS = "agent://www.arbi.com/BehaviorInterface";
	public static final String BEHAVIOR_INTERFACE_DATA_SOURCE_ADDRESS = "ds://www.arbi.com/BehaviorInterface";
	
	public static final String SERVER_ADDRESS = "127.0.0.1";
	public static final int SERVER_PORT_EPSON = 61116;
	public static final int SERVER_PORT_UR = 61115;
	public static final int SERVER_PORT_PRODUCTION_FACILITY = 61316;
	public static final BrokerType BROKER_TYPE = BrokerType.ACTIVEMQ;
	
  	public static final int EPSON_ARM_PORT = 1470;
	public static final String EPSON_GRIPPER_PORT = "COM13";
	
	public static final int UR_ARM_PORT = 1471;
	public static final String UR_ARM_SERVER_ADDRESS = "192.168.10.30";
	public static final int UR_ARM_SERVER_PORT = 29999;
	public static final String UR_GRIPPER_PORT = "COM12";
	
	public static final String LABEL_PRINTER_PORT = "COM4";

	public static final String PCB_TRAY_PORT = "COM5";
	public static final String HOUSING_TRAY_PORT = "COM6";
	public static final String LENS_TRAY_PORT = "COM7";
	public static final String FRONT_TRAY_PORT = "COM8";
	public static final String SUB_PC_PORT = "COM9";
	
	public static final int BEATRATE = 115200;
	
}
