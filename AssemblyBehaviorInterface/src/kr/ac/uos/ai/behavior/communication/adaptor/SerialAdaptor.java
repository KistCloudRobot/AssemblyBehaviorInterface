package kr.ac.uos.ai.behavior.communication.adaptor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fazecast.jSerialComm.SerialPort;

import kr.ac.uos.ai.behavior.Configuration;
import kr.ac.uos.ai.behavior.communication.Communication;

public class SerialAdaptor extends Thread implements Adaptor{
	private String portName;
	private int baudRate = 115200;
	private Communication communication;
	private SerialPort serialPort;
	
	private InputStream 	inputStream;
	private OutputStream 	outputStream;
	
	
	public SerialAdaptor(Communication communication, String portName) {
		this.communication = communication;
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
			System.out.println("Failed to find serial port : " + portName);
			return;
		}
		
		serialPort.setBaudRate(baudRate);
		if (!serialPort.openPort()) {
            System.err.println("Cannot open port : " + portName);
            return;
        }

        inputStream = serialPort.getInputStream();
        outputStream = serialPort.getOutputStream();
        
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
		System.out.println("serial message :" + message);
		communication.onMessage(message);
	}
	
	
	public void send(String message) {
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
