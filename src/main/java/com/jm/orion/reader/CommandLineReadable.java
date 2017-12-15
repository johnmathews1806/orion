package com.jm.orion.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public interface CommandLineReadable {

	public List<String> readInput() throws IOException;
	
	public void close();
	
	public String getScannerName();
	
		
}
