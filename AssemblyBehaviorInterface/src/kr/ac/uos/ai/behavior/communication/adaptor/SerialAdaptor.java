package kr.ac.uos.ai.behavior.communication.adaptor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fazecast.jSerialComm.SerialPort;

import kr.ac.uos.ai.behavior.Configuration;
import kr.ac.uos.ai.behavior.communication.Communication;

public class SerialAdaptor extends Adaptor{
	private String portName;
	private int baudRate = 115200;
	private SerialPort serialPort;
	
	private InputStream 	inputStream;
	private OutputStream 	outputStream;
	
	
	public SerialAdaptor(Communication communication, String portName) {
		super(communication);
		this.portName = portName;
		this.baudRate = Configuration.BEATRATE;
	}
	
	public void connect() {
		SerialPort[] ports = SerialPort.getCommPorts();
		for (SerialPort port : ports) {
			if (port.getSystemPortName().equals(portName)) {
				this.serialPort = port;
			}
		}
		
		if (serialPort == null) {
			System.out.println("[SerialAdaptor] Failed to find serial port : " + portName);
			return;
		}
		
		serialPort.setBaudRate(baudRate);
		if (!serialPort.openPort()) {
			System.out.println("[SerialAdaptor] Cannot open port : " + portName);
            return;
        }

        inputStream = serialPort.getInputStream();
        outputStream = serialPort.getOutputStream();
        System.out.println("[SerialAdaptor] serial port connected!");
        this.start();
	}
	
	public void run() {
		byte[] buffer = new byte[1024];
//        int len = -1;
    	StringBuilder receivedMessage = new StringBuilder();
        try {
			while (true) {
                if (inputStream.available() > 0) {
                    
                    int bytesRead = inputStream.read(buffer);
                    String receivedData = new String(buffer, 0, bytesRead);
                    receivedMessage.append(receivedData);

                    String[] messages = receivedMessage.toString().split("\r\n");
                    for (String message : messages) {

                    	System.out.println("[SerialAdaptor] message received : " +message);
                    	handleMessage(message);
                    }

                    receivedMessage = new StringBuilder();
                    if (messages.length > 0 && !receivedData.endsWith("\r\n")) {
                        receivedMessage.append(messages[messages.length - 1]);
                    }
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	

	private void handleMessage(String message) {
		communication.onMessage(message);
	}
	
	
	public void send(String message) {
		System.out.println("[SerialAdaptor] send message : " + message);
		message = message + "\r\n";
		try {
			outputStream.write(message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SerialAdaptor adaptor = new SerialAdaptor(null, "COM2");
		adaptor.connect();
//		adaptor.send("<OUT01=1/>\r\n");
//
//		adaptor.send("<I>CHECK</I>\r\n");
	}
}
