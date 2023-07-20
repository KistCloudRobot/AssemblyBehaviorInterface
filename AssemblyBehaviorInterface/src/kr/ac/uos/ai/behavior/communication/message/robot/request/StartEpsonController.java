package kr.ac.uos.ai.behavior.communication.message.robot.request;

import kr.ac.uos.ai.arbi.model.Expression;
import kr.ac.uos.ai.arbi.model.GLFactory;
import kr.ac.uos.ai.arbi.model.GeneralizedList;
import kr.ac.uos.ai.behavior.communication.message.robot.RobotMessage;
import kr.ac.uos.ai.behavior.communication.message.robot.acknowledge.AckControllerInitMessage;
import kr.ac.uos.ai.behavior.communication.message.value.ActionType;

public class StartEpsonController extends RobotMessage{


	public StartEpsonController(String sender, String actionID) {
		super(sender ,ActionType.StartEpsonController, actionID, null);
	}

	@Override
	public String getMessage() {
		String result = "<OUT04=1/>";
		return result;
	}
	
	@Override
	public String makeResponse() {
		String res = "(fail)";
		Expression actionID = GLFactory.newExpression(GLFactory.newValue(this.getActionID()));
		Expression actionResult = null;

		if (responseMessage instanceof AckControllerInitMessage) {
			String response = ((AckControllerInitMessage) responseMessage).getResponse();
			if (response.equals("<OUT04=1_ACK/>")) {

				actionResult = GLFactory.newExpression(GLFactory.newValue("success"));
			} else {

				actionResult = GLFactory.newExpression(GLFactory.newValue("fail"));
			}
		}
		GeneralizedList gl = GLFactory.newGL("ActionResult", actionID, actionResult);
		res = gl.toString();
		return res;
	}
}
