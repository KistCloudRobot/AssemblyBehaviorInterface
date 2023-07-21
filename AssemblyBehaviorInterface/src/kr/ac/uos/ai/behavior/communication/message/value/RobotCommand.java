package kr.ac.uos.ai.behavior.communication.message.value;

import java.util.HashMap;
import java.util.Map;

public enum RobotCommand {

	EpsonHome(11),	PCBGet(12), PCBScan(13),
	HousingGet(14), HousingLabel(15), EpsonPutWait(16),
	HousingPut(17), PCBPut(18),
	URHome(21), LensGet(22), FrontGet(24),
	URPutWait(26), LensPut(27), FrontPut(28),
	
	StatusRequest(91), StatusReturn(92);
	
	private final int value;
	private static final Map<Integer, RobotCommand> BY_VALUE = new HashMap<>();
	
	static {
		for (RobotCommand id : values()) {
			BY_VALUE.put(id.value, id);
		}
	}
	
	
	RobotCommand(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public static RobotCommand getEnum(int i) {
		return BY_VALUE.get(i);
	}
	
}
