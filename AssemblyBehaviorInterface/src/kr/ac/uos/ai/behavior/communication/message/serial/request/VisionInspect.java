package kr.ac.uos.ai.behavior.communication.message.serial.request;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.behavior.communication.message.serial.SerialMessage;
import kr.ac.uos.ai.behavior.communication.message.serial.response.TrayResponseMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public class VisionInspect extends SerialMessage {

	public VisionInspect(String sender, String actionID) {
		super(sender, ActionType.RiseGuideJig, actionID);
	}

	@Override
	public String makeResponse() {
		GeneralizedList gl = null;
		Expression actionID = null;
		Expression actionResult = null;
		if(responseMessage instanceof TrayResponseMessage) {
			actionID = GLFactory.newExpression(GLFactory.newValue(this.getActionID()));
			if(((TrayResponseMessage) responseMessage).getResponse().equals("<CHK>TEST=OK</CHK>")) {
				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else {
				actionResult = GLFactory.newExpression(GLFactory.newValue("fail"));
			}
			gl = GLFactory.newGL("ActionResult", actionID, actionResult);
		}  else {
			System.out.println("something is wrong " + responseMessage.toString());
			gl = GLFactory.newGL("fail");
		}
		
		return gl.toString();
	}

	@Override
	public String getMessage() {
		return "<CHK>TEST=START</CHK>";
	}

}
