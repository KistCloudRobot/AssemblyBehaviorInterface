package kr.ac.uos.ai.behavior.communication;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.request.CheckDockingPlateStatus;
import kr.ac.uos.ai.behavior.communication.message.serial.request.DetachDockingPlateCenterInLoadingBay;
import kr.ac.uos.ai.behavior.communication.message.serial.request.DetachDockingPlateSideFixturesInLoadingBay;
import kr.ac.uos.ai.behavior.communication.message.serial.request.GraspDockingPlateInLoadingBay;
import kr.ac.uos.ai.behavior.communication.message.serial.request.HoldDockingPlateSideFixturesInLoadingBay;
import kr.ac.uos.ai.behavior.communication.message.serial.request.LowerDockingPlateInLoadingBay;
import kr.ac.uos.ai.behavior.communication.message.serial.request.MoveDockingPlateToDischargeArea;
import kr.ac.uos.ai.behavior.communication.message.serial.request.MoveDockingPlateToTheFront;
import kr.ac.uos.ai.behavior.communication.message.serial.request.RaiseDockingHoleFixturesInLoadingBay;
import kr.ac.uos.ai.behavior.communication.message.serial.request.RaiseDockingPlate;
import kr.ac.uos.ai.behavior.communication.message.serial.response.TrayResponseMessage;

public class TrayCommunication extends SerialCommunication {

	private SerialMessage waitingResponse;
	
	public TrayCommunication(BehaviorInterface bi, String portName) {
		super(bi, portName);
	}

	@Override
	public void onMessage(String message) {
		message = removeEndLineMarker(message);
		if (this.waitingResponse != null && message.startsWith("<") && message.endsWith(">")) {
			TrayResponseMessage m = parseMessage(message);
			this.waitingResponse.setResponse(m);
			behaviorInterface.sendMessage(waitingResponse.getSender(), waitingResponse.getResponse());
			
			this.waitingResponse = null;
		} else System.out.println("wrong message from tray : " + message);
	}
	
	private TrayResponseMessage parseMessage(String message) {
		TrayResponseMessage result = new TrayResponseMessage(message);
		return result;
	}
	
	public String checkDockingPlateStatus(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new CheckDockingPlateStatus(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}


	public String raiseDockingPlate(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new RaiseDockingPlate(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}


	public String moveDockingPlateToDischargeArea(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new MoveDockingPlateToDischargeArea(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
	
	public String graspDockingPlateInLoadingBay(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new GraspDockingPlateInLoadingBay(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
	public String detachDockingPlateSideFixturesInLoadingBay(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new DetachDockingPlateSideFixturesInLoadingBay(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
	
	public String lowerDockingPlateInLoadingBay(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new LowerDockingPlateInLoadingBay(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
	
	public String holdDockingPlateSideFixturesInLoadingBay(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new HoldDockingPlateSideFixturesInLoadingBay(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
	
	public String detachDockingPlateCenterInLoadingBay(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new DetachDockingPlateCenterInLoadingBay(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
	public String raiseDockingHoleFixturesInLoadingBay(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new RaiseDockingHoleFixturesInLoadingBay(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
	public String moveDockingPlateToTheFront(String sender, String actionID) {
		if(waitingResponse == null) {
			this.waitingResponse = new MoveDockingPlateToTheFront(sender, actionID);
			this.adaptor.send(waitingResponse.getMessage());
			return "(ok)";
		}
		
		return "(fail)";
	}
}
