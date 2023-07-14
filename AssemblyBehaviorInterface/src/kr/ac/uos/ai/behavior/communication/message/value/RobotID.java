package kr.ac.uos.ai.behavior.communication.message.value;

import java.util.HashMap;
import java.util.Map;

public enum RobotID {

	Epson(1),	UR(2);
	
	private final int value;
	private static final Map<Integer, RobotID> BY_VALUE = new HashMap<>();
	
	static {
		for (RobotID id : values()) {
			BY_VALUE.put(id.value, id);
		}
	}
	
	
	RobotID(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public static RobotID getEnum(int i) {
		return BY_VALUE.get(i);
	}
	
}
