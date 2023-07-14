package test;

import kr.ac.uos.ai.behavior.BehaviorInterface;
import kr.ac.uos.ai.behavior.communication.SerialCommunication;

public class SerialTest extends SerialCommunication {
	public SerialTest(BehaviorInterface bi, String portName) {
		super(bi, portName);
	}

	public static void main(String[] args) {
		SerialTest test = new SerialTest(null, "COM4");
		test.connect();
	}

	@Override
	public void onMessage(String message) {
		System.out.println(message);
		if (message.equals("<I>CHECK</I>")) {
			adaptor.send("<I>0:1:0:0:0:1:0:1:1:1:1:1:1:1:1:1</I>\r\n");
		}
	}
}
