package com.jm.orion.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandLineReader implements CommandLineReadable {
	
	String input = null;
	String[] tokens =  null;
	
	public List<String> readInput() throws IOException{		
		input = (new BufferedReader(new InputStreamReader(System.in))).readLine();		
		tokens = input.split(" ");
		List<String> tokenList =Arrays.asList(tokens);
		return tokenList;			
	}
	
	public void close(){
		
	}
	
	public String getScannerName(){
		return "No Scanner used";
	}

}
