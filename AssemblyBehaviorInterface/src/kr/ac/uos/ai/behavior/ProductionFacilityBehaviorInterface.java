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
		pcbTrayCommunication.connect();
		housingTrayCommunication.connect();
		lensTrayCommunication.connect();
		frontTrayCommunication.connect();
	}
	
	
	@Override
	public String onRequest(String sender, String request) {
		logger.log("[request]\t: " + request + " timestamp : " + System.currentTimeMillis());
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
				
				
			default :
				response = "(wrongRequest)";
				break;
			}
			logger.log("[request] response : " + response);
			return response;
		} catch (ParseException e) {
			e.printStackTrace();
			return "(fail)";
		}
	}

}
