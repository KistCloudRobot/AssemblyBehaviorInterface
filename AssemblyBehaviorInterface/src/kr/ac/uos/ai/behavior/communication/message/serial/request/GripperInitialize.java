package kr.ac.uos.ai.behavior.communication.message.serial.request;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.GripperInitResponseMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public class GripperInitialize extends SerialMessage {

	public GripperInitialize(String sender, String actionID) {
		super(sender, ActionType.InitGripper,actionID);
	}
	
	public String getMessage() {

		return "<O>0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:0</O>";
	}


	@Override
	public String makeResponse() {
		GeneralizedList gl = null;
		Expression actionID = null;
		Expression actionResult = null;
		if (responseMessage == null) {
			gl = GLFactory.newGL("ok");
		} else if(responseMessage instanceof GripperInitResponseMessage) {
			actionID = GLFactory.newExpression(GLFactory.newValue(this.getActionID()));
			String response = ((GripperInitResponseMessage) responseMessage).getResponse();
			if (response.equals("<O>ACK</O>")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			}	
			gl = GLFactory.newGL("ActionResult", actionID, actionResult);
		}  else {
			System.out.println("something is wrong " + responseMessage.toString());
			gl = GLFactory.newGL("fail");
		}
		
		return gl.toString();
	}

}
