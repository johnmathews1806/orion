package com.jm.orion;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jm.orion.function.Anagram;
import com.jm.orion.function.Authenticate;
import com.jm.orion.Menu;
import com.jm.orion.file.FileHelper;
import com.jm.orion.function.NumberManipulator;
import com.jm.orion.function.Zipper;
import com.jm.orion.logging.LogConfig;
import com.jm.orion.function.FileManipulator;
import com.jm.orion.reader.CommandLineNewReader;
import com.jm.orion.reader.CommandLineReadable;

public class UserInterface {
	String[] params = {"sdsdf","8"};

	public static void main(String[] args){		
		
		//**Get logger and set log level.**
		LogConfig.setupLogging();
		Logger logger = Logger.getLogger("orion.utility.log");
		
		FileHelper fh = new FileHelper();
/*		fh.storeCredentials("john", "abcdef");
		fh.storeCredentials("abraham", "123456");
		fh.storeCredentials("aaron", "1234");*/
		

				
		//**Display Welcome Menu**
		Menu menu = new Menu();		
		menu.showWelcomeMenu();
		Boolean continue_menu = true;
		
		//**Prompt user to continue**		
		CommandLineReadable cr = new CommandLineNewReader();
		
		//fh.serializeObject(new Date());
		fh.serializeObject(cr);
		fh.serializeObject(cr);
		
		//**Authentication**
		Authenticate auth = new Authenticate();
		auth.verify();
		//auth.consent(cr);
		
		
		UserInterface ui = new UserInterface();
		NumberManipulator nm = new NumberManipulator();
		FileManipulator fm = new FileManipulator();		
		Zipper zip = new Zipper();

		//**Show main menu and prompt user for menu selection**
		Scanner scanner = new Scanner(System.in);		
		
		try{
			do{
				try{
					//** For finding the Operating System ** this should be used before invoking the next line since its platform dependent.
					//System.getProperty("os.name") 

					new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
					menu.showMainMenu();				

					logger.log(Level.INFO, "BEFORE SWITCH: "+scanner.toString());

					switch (scanner.nextInt()) {
					case 1:		
						nm.addNumberfromCommandLine();					
						break;
					case 2:
						Anagram ag = new Anagram();					
						ag.process(ui.params);
						break;
					case 3:
						fm.processFile();
						break;
					case 4:
						zip.zipFile();
						break;
					case 5:
						fm.reverseNumbersInFile();
						break;
					case 6:
						SOP("Select 6");
						break;
					case 0:		
						SOP("Exiting...");
						continue_menu=false;					
						break;
					default:
						SOP("Please enter a valid code");
						break;
					}	
				}catch(InputMismatchException e){
					SOP("Please enter a valid code");
				}

				if(continue_menu){				
					scanner = new Scanner(System.in);
					SOP("Continue ? (Enter any key)");					
					scanner.hasNextLine();				
					logger.log(Level.INFO, "AFTER CONTINUE: "+scanner.toString());					
				}else{
					scanner.close();
					logger.log(Level.INFO, "ELSE: "+scanner.toString());					
				}

			}while(continue_menu);
		}catch(InterruptedException e1){			
			logger.log(Level.FINER,"Interrupted");
		}catch(IOException e2){			
			logger.log(Level.FINER,"Error in IO");
		}catch(InputMismatchException e){
			logger.log(Level.FINER,"Error in IO");
		}finally{
			if(cr !=null){
				cr.close();
			}				
			logger.log(Level.INFO,"FINALLY: "+cr.getScannerName());
		}

	}

	public static void SOP(Object obj){
		System.out.println(obj);
	}

	public static void SOL(Object obj){
		System.out.print(obj);
	}
}
