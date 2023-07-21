package kr.ac.uos.ai.behavior.communication.message.value;

import java.util.HashMap;
import java.util.Map;

public enum RobotPosition {

	EpsonHome(11),	PCBGet(12), PCBScan(13),
	HousingGet(14), HousingLabel(15), EpsonPutWait(16),
	HousingPut(17), PCBPut(18),
	URHome(21), LensGet(22), FrontGet(23),
	URPutWait(24), LensPut(25), FrontPut(28);
	
	private final int value;
	private static final Map<Integer, RobotPosition> BY_VALUE = new HashMap<>();
	
	static {
		for (RobotPosition id : values()) {
			BY_VALUE.put(id.value, id);
		}
	}
	
	RobotPosition(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public static RobotPosition getEnum(int i) {
		return BY_VALUE.get(i);
	}
	
}
