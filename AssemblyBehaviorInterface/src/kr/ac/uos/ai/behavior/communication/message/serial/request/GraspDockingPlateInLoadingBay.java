package kr.ac.uos.ai.behavior.communication.message.serial.request;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.TrayResponseMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public class GraspDockingPlateInLoadingBay extends SerialMessage {

	public GraspDockingPlateInLoadingBay(String sender, String actionID) {
		super(sender, ActionType.GraspDockingPlateInLoadingBay, actionID);
	}

	@Override
	public String makeResponse() {
		GeneralizedList gl = null;
		Expression actionID = null;
		Expression actionResult = null;
		if (responseMessage == null) {
			gl = GLFactory.newGL("ok");
		} else if (responseMessage instanceof TrayResponseMessage) {
			actionID = GLFactory.newExpression(GLFactory.newValue(this.getActionID()));
			String msg = ((TrayResponseMessage) responseMessage).getResponse();
			if (msg.equals("<OUT02=1_ACK/>")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else actionResult = GLFactory.newExpression(GLFactory.newValue("fail"));
		}
		gl = GLFactory.newGL("ActionResult", actionID, actionResult);
		return gl.toString();
	}

	@Override
	public String getMessage() {
		return "<OUT02=1/>";
	}

}
