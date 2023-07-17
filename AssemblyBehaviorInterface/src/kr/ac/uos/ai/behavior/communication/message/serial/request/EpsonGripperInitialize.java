package kr.ac.uos.ai.behavior.communication.message.serial.request;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.GripperResponseMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public class EpsonGripperInitialize extends SerialMessage {

	public EpsonGripperInitialize(String sender, String actionID) {
		super(sender, ActionType.EpsonGripperInitialize,actionID);
	}
	
	public String getMessage() {

		return "<OUT04=1/>";
	}


	@Override
	public String makeResponse() {
		GeneralizedList gl = null;
		Expression actionID = null;
		Expression actionResult = null;
		if (responseMessage == null) {
			gl = GLFactory.newGL("ok");
		} else if(responseMessage instanceof GripperResponseMessage) {
			actionID = GLFactory.newExpression(GLFactory.newValue(this.getActionID()));
			String[] response = ((GripperResponseMessage) responseMessage).getResponse();
			if (response[9].equals("1")) {
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
