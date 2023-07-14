package kr.ac.uos.ai.behavior.communication.message.value;

import java.util.HashMap;
import java.util.Map;

public enum RobotState {

	busy(1),	ready(2);
	
	private final int value;
	private static final Map<Integer, RobotState> BY_VALUE = new HashMap<>();
	
	static {
		for (RobotState id : values()) {
			BY_VALUE.put(id.value, id);
		}
	}
	
	
	RobotState(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public static RobotState getEnum(int i) {
		return BY_VALUE.get(i);
	}
	
}
