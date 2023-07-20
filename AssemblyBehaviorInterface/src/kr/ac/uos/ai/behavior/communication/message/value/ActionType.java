package kr.ac.uos.ai.behavior.communication.message.value;

public enum ActionType {

	
	MoveToPosition,				MoveToTray,
	Grasp,						Release,
	RotateGripper,				
	
	StartEpsonController,
	InitGripper,
	StartURController,
	InitURGripper,
	
	RiseGuideJig,				VisionInspect,
	CheckLabel,
	
	RotateToLabelPosition,		RotateToAttachPosition,
	StartVacuum,				StopVacuum,
	LiftUp,						LiftDown;
	
}
