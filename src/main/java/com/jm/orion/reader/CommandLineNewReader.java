package com.jm.orion.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CommandLineNewReader implements CommandLineReadable, java.io.Serializable {

	String input = null;
	String[] tokens =  null;
	Scanner scanner;

	public List<String> readInput() throws IOException {
		scanner = new Scanner(System.in);
		input =scanner.nextLine();
		//scanner.close();
		tokens = input.split(" ");
		List<String> tokenList =Arrays.asList(tokens);
		//System.out.println(tokenList);
		return tokenList;
	}
	
	public void close(){
		scanner.close();
	}

	public String getScannerName(){
		return this.scanner.toString();
	}


}
