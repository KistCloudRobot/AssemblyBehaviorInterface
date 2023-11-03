package kr.ac.uos.ai.behavior;

import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.arbi.model.parser.ParseException;
import kr.ac.uos.ai.behavior.communication.SubPCCommunication;
import kr.ac.uos.ai.behavior.communication.TrayCommunication;
import kr.ac.uos.ai.behavior.communication.LabelPrinterCommunication;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public class ProductionFacilityBehaviorInterface extends BehaviorInterface{

	private LabelPrinterCommunication labelPrinterCommunication;
	private SubPCCommunication subPCCommunication;
	
	private TrayCommunication pcbTrayCommunication;
	private TrayCommunication housingTrayCommunication;
	private TrayCommunication lensTrayCommunication;
	private TrayCommunication frontTrayCommunication;
	
	public ProductionFacilityBehaviorInterface(String brokerAddress, int brokerPort, String trayPort, String subPCPort, String pcbTrayPort, String housingTrayPort, String lensTrayPort, String frontTrayPort) {
		super(brokerAddress, brokerPort);
		
		labelPrinterCommunication = new LabelPrinterCommunication(this, trayPort);
		subPCCommunication = new SubPCCommunication(this, subPCPort);
		pcbTrayCommunication = new TrayCommunication(this, pcbTrayPort);
		housingTrayCommunication = new TrayCommunication(this, housingTrayPort);
		lensTrayCommunication = new TrayCommunication(this, lensTrayPort);
		frontTrayCommunication = new TrayCommunication(this, frontTrayPort);
		
		labelPrinterCommunication.connect();
		subPCCommunication.connect();
		
//		pcbTrayCommunication.connect();
//		housingTrayCommunication.connect();
//		lensTrayCommunication.connect();
//		frontTrayCommunication.connect();
		
	}
	
	
	@Override
	public String onRequest(String sender, String request) {
		System.out.println("[request]\t: " + request + " timestamp : " + System.currentTimeMillis());
		try {
			GeneralizedList gl = GLFactory.newGLFromGLString(request);
			ActionType actionType = ActionType.valueOf(gl.getName());
			String actionID = gl.getExpression(0).asValue().stringValue();
			
			String response = null;
			
			switch(actionType) {
			case CheckSubPC :
				response = subPCCommunication.checkSubPC(sender, actionID);
				break;
				
			case RiseGuideJig :
				response = subPCCommunication.riseGuideJig(sender, actionID);
				break;
				
			case VisionInspect :
				response = subPCCommunication.visionInspect(sender, actionID);
				break;
				
			case CheckLabel :
				response = subPCCommunication.checkLabel(sender, actionID);
				break;
				
			case RotateToAttachPosition :
				response = labelPrinterCommunication.rotateToAttachPosition(sender, actionID);
				break;
				
			case RotateToLabelPosition :
				response = labelPrinterCommunication.rotateToLabelPosition(sender, actionID);
				break;
				
			case StartVacuum :
				response = labelPrinterCommunication.startVacuum(sender, actionID);
				break;
				
			case StopVacuum :
				response = labelPrinterCommunication.stopVacuum(sender, actionID);
				break;
				
			case LiftUp :
				response = labelPrinterCommunication.liftUp(sender, actionID);
				break;
				
			case LiftDown :
				response = labelPrinterCommunication.liftDown(sender, actionID);
				break;
				
			case CheckDockingPlateStatus :
				String part = gl.getExpression(1).asValue().stringValue();
				if (part.equals("PCB")) {
					pcbTrayCommunication.checkDockingPlateStatus(sender, actionID);
				} else if (part.equals("Housing")) {
					housingTrayCommunication.checkDockingPlateStatus(sender, actionID);
				} else if (part.equals("Lens")) {
					lensTrayCommunication.checkDockingPlateStatus(sender, actionID);
				} else if (part.equals("Front")) {
					frontTrayCommunication.checkDockingPlateStatus(sender, actionID);
				}
				break;
				
			case RaiseDockingPlate:
				part = gl.getExpression(1).asValue().stringValue();
				if (part.equals("PCB")) {
					pcbTrayCommunication.raiseDockingPlate(sender, actionID);
				} else if (part.equals("Housing")) {
					housingTrayCommunication.raiseDockingPlate(sender, actionID);
				} else if (part.equals("Lens")) {
					lensTrayCommunication.raiseDockingPlate(sender, actionID);
				} else if (part.equals("Front")) {
					frontTrayCommunication.raiseDockingPlate(sender, actionID);
				}
				break;
				
			case MoveDockingPlateToDischargeArea :
				part = gl.getExpression(1).asValue().stringValue();
				if (part.equals("PCB")) {
					pcbTrayCommunication.moveDockingPlateToDischargeArea(sender, actionID);
				} else if (part.equals("Housing")) {
					housingTrayCommunication.moveDockingPlateToDischargeArea(sender, actionID);
				} else if (part.equals("Lens")) {
					lensTrayCommunication.moveDockingPlateToDischargeArea(sender, actionID);
				} else if (part.equals("Front")) {
					frontTrayCommunication.moveDockingPlateToDischargeArea(sender, actionID);
				}
				break;
				
			case GraspDockingPlateInLoadingBay :
				part = gl.getExpression(1).asValue().stringValue();
				if (part.equals("PCB")) {
					pcbTrayCommunication.graspDockingPlateInLoadingBay(sender, actionID);
				} else if (part.equals("Housing")) {
					housingTrayCommunication.graspDockingPlateInLoadingBay(sender, actionID);
				} else if (part.equals("Lens")) {
					lensTrayCommunication.graspDockingPlateInLoadingBay(sender, actionID);
				} else if (part.equals("Front")) {
					frontTrayCommunication.graspDockingPlateInLoadingBay(sender, actionID);
				}
				break;
				
			case DetachDockingPlateSideFixturesInLoadingBay :
				part = gl.getExpression(1).asValue().stringValue();
				if (part.equals("PCB")) {
					pcbTrayCommunication.detachDockingPlateSideFixturesInLoadingBay(sender, actionID);
				} else if (part.equals("Housing")) {
					housingTrayCommunication.detachDockingPlateSideFixturesInLoadingBay(sender, actionID);
				} else if (part.equals("Lens")) {
					lensTrayCommunication.detachDockingPlateSideFixturesInLoadingBay(sender, actionID);
				} else if (part.equals("Front")) {
					frontTrayCommunication.detachDockingPlateSideFixturesInLoadingBay(sender, actionID);
				}
				break;
				
			case LowerDockingPlateInLoadingBay :
				part = gl.getExpression(1).asValue().stringValue();
				if (part.equals("PCB")) {
					pcbTrayCommunication.lowerDockingPlateInLoadingBay(sender, actionID);
				} else if (part.equals("Housing")) {
					housingTrayCommunication.lowerDockingPlateInLoadingBay(sender, actionID);
				} else if (part.equals("Lens")) {
					lensTrayCommunication.lowerDockingPlateInLoadingBay(sender, actionID);
				} else if (part.equals("Front")) {
					frontTrayCommunication.lowerDockingPlateInLoadingBay(sender, actionID);
				}
				break;
				
			case HoldDockingPlateSideFixturesInLoadingBay :
				part = gl.getExpression(1).asValue().stringValue();
				if (part.equals("PCB")) {
					pcbTrayCommunication.holdDockingPlateSideFixturesInLoadingBay(sender, actionID);
				} else if (part.equals("Housing")) {
					housingTrayCommunication.holdDockingPlateSideFixturesInLoadingBay(sender, actionID);
				} else if (part.equals("Lens")) {
					lensTrayCommunication.holdDockingPlateSideFixturesInLoadingBay(sender, actionID);
				} else if (part.equals("Front")) {
					frontTrayCommunication.holdDockingPlateSideFixturesInLoadingBay(sender, actionID);
				}
				break;
				
			case DetachDockingPlateCenterInLoadingBay :
				part = gl.getExpression(1).asValue().stringValue();
				if (part.equals("PCB")) {
					pcbTrayCommunication.detachDockingPlateCenterInLoadingBay(sender, actionID);
				} else if (part.equals("Housing")) {
					housingTrayCommunication.detachDockingPlateCenterInLoadingBay(sender, actionID);
				} else if (part.equals("Lens")) {
					lensTrayCommunication.detachDockingPlateCenterInLoadingBay(sender, actionID);
				} else if (part.equals("Front")) {
					frontTrayCommunication.detachDockingPlateCenterInLoadingBay(sender, actionID);
				}
				break;
				
			case RaiseDockingHoleFixturesInLoadingBay :
				part = gl.getExpression(1).asValue().stringValue();
				if (part.equals("PCB")) {
					pcbTrayCommunication.raiseDockingHoleFixturesInLoadingBay(sender, actionID);
				} else if (part.equals("Housing")) {
					housingTrayCommunication.raiseDockingHoleFixturesInLoadingBay(sender, actionID);
				} else if (part.equals("Lens")) {
					lensTrayCommunication.raiseDockingHoleFixturesInLoadingBay(sender, actionID);
				} else if (part.equals("Front")) {
					frontTrayCommunication.raiseDockingHoleFixturesInLoadingBay(sender, actionID);
				}
				break;
				
			case MoveDockingPlateToTheFront :
				part = gl.getExpression(1).asValue().stringValue();
				if (part.equals("PCB")) {
					pcbTrayCommunication.moveDockingPlateToTheFront(sender, actionID);
				} else if (part.equals("Housing")) {
					housingTrayCommunication.moveDockingPlateToTheFront(sender, actionID);
				} else if (part.equals("Lens")) {
					lensTrayCommunication.moveDockingPlateToTheFront(sender, actionID);
				} else if (part.equals("Front")) {
					frontTrayCommunication.moveDockingPlateToTheFront(sender, actionID);
				}
				break;
				
				
			default :
				response = "(wrongRequest)";
				break;
			}
			System.out.println("[request] response : " + response);
			return response;
		} catch (ParseException e) {
			e.printStackTrace();
			return "(fail)";
		}
	}

}
