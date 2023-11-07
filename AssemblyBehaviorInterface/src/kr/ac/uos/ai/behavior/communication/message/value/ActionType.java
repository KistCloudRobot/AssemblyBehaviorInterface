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
	CheckJig,
	
	RotateToLabelPosition,		RotateToAttachPosition,
	StartVacuum,				StopVacuum,
	LiftUp,						LiftDown,
	
	CheckDockingPlateStatus,
	RaiseDockingPlate,			MoveDockingPlateToDischargeArea,
	LowerDockingPlate,
	GraspDockingPlateInLoadingBay,			DetachDockingPlateSideFixturesInLoadingBay,
	LowerDockingPlateInLoadingBay,			HoldDockingPlateSideFixturesInLoadingBay,
	DetachDockingPlateCenterInLoadingBay, 	RaiseDockingHoleFixturesInLoadingBay,
	MoveDockingPlateToTheFront;
	
	
	
	
}
