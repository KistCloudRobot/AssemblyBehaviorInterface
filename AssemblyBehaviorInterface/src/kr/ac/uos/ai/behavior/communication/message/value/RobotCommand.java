package kr.ac.uos.ai.behavior.communication.message.value;

import java.util.HashMap;
import java.util.Map;

public enum RobotCommand {

	EpsonHome(11),	PCBGet(12), PCBBarcode(13),
	HousingGet(14), HousingLabel(15), EpsonPutWait(16),
	HousingPut(17), PCBPut(18),
	URHome(21), LensGet(22), CoverGet(23),
	URPutWait(24), LensPut(25), CoverPut(28),
	
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
