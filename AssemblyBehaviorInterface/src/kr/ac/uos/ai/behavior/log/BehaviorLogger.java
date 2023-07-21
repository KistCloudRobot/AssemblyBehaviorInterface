package kr.ac.uos.ai.behavior.log;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BehaviorLogger {
	
	Logger logger = Logger.getLogger("BehaviorLogger");
	private static BehaviorLogger instance = new BehaviorLogger();
	
	public static final String fileName = "log.txt";
	
	private FileHandler logFile;
	private ConsoleHandler logConsole;
	
	private boolean _fileLog = false;
	private boolean _consoleLog = true;
	
	
	private BehaviorLogger() {
		if (_fileLog) {
			try {
				logFile = new FileHandler(fileName);
				
				logFile.setFormatter(new SimpleFormatter());
				logFile.setLevel(Level.ALL);
				logger.addHandler(logFile);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (_consoleLog) {
			try {
				logConsole = new ConsoleHandler();
				
				logConsole.setFormatter(new SimpleFormatter());
				logConsole.setLevel(Level.ALL);
				logger.addHandler(logConsole);
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static BehaviorLogger getLogger() {
		return instance;
	}
	
	public void log(String msg) {
		logger.info(msg);
	}
	
	public void warning(String msg) {
		logger.warning(msg);
	}
}
