package com.jm.orion.logging;

import static com.jm.orion.UserInterface.SOP;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogConfig {

	public static void setupLogging(){
		
		//**Setting up the logger**
		try {
			Handler handler = new FileHandler("D:/OutFile.log");
			Logger.getLogger("").addHandler(handler);
			SOP("log file created");

		} catch (IOException e) {
			SOP("Error in  file creation");
			Logger logger = Logger.getLogger("orion.utility.log"); 
			logger.log(Level.SEVERE, e.getMessage());
		}
		//**Get logger and set log level.**
		Logger logger = Logger.getLogger("orion.utility.log");
		logger.setLevel(Level.INFO);
	}

}
