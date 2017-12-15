package com.jm.orion.function;

import static com.jm.orion.UserInterface.SOP;
import java.io.Console;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jm.orion.file.FileHelper;
import com.jm.orion.reader.CommandLineReadable;

public class Authenticate {

	Logger logger = Logger.getLogger("orion.utility.log");
	
	public void verify(){
		//**Authentication**
				Console c = System.console();
		        if (c == null) {
		            System.err.println("No console.");
		            System.exit(1);
		        }
		                
		        String login = c.readLine("Enter your login: ");
		        char [] providedPassword = c.readPassword("Enter your password: ");
		        String a = new String(providedPassword);
		        
		        
		        FileHelper fh = new FileHelper();
		        Map credentialStore = fh.getCredentialStore();
		        //SOP(credentialStore);
		        if(!credentialStore.containsKey(login) 
		        		|| !credentialStore.get(login).equals(new String(providedPassword))){
		        		
		        	logger.log(Level.INFO,"user id NOT present in store"+login);		        	
		        	logger.log(Level.INFO,"password verified NOT ok: "+providedPassword);		        	
		        	System.err.println("Incorrect credentials.");
		        	System.exit(1);		        		
		        }
/*		        String storedLogin = "john";
		        String storedPassword = "abcd";        
		        
		        if (!(storedPassword.equals(String.valueOf(providedPassword)) 
		        		&& storedLogin.equals(String.valueOf(login)))){
		        	SOP(providedPassword);
		        	SOP(login);
		        	System.err.println("Incorrect credentials.");
		            System.exit(1);
		        }    */  
	}
	
	public void consent(CommandLineReadable cr){
		SOP("Enter Y to continue..");				
		
		try{
			List<String> tokens = cr.readInput();

			if(tokens.size()>1){
				SOP("More than a character entered. Exiting...");			
				System.exit(1);
			}

			if(!"Y".equalsIgnoreCase(tokens.get(0))){
				SOP("Exiting...");				
				System.exit(1);
			}

		}catch(IOException e){
			SOP("Error in reading input");
			logger.log(Level.SEVERE, "Error in reading input");
		}
	}
	

}
