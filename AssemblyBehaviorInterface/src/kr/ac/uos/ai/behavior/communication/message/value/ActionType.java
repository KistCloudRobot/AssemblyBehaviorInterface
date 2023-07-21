package kr.ac.uos.ai.behavior.communication.message.value;

public enum ActionType {

	
	MoveToPosition,				MoveToTray,
	Grasp,						Release,
	Perceive,
	Rotate,				
	CheckRobotReady,
	InitGripper,
	
	CheckTray,
	
	CheckSubPC,
	
	RiseGuideJig,				VisionInspect,
	CheckLabel,
	
	RotateToLabelPosition,		RotateToAttachPosition,
	StartVacuum,				StopVacuum,
	LiftUp,						LiftDown; 
	
	
	
}
