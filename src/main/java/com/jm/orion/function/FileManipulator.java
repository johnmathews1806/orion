package com.jm.orion.function;

import static com.jm.orion.UserInterface.SOP;
import static com.jm.orion.UserInterface.SOL;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jm.orion.file.FileHelper;
import com.jm.orion.reader.CommandLineNewReader;
import com.jm.orion.reader.CommandLineReadable;

public class FileManipulator {	

	String fileName ;
	int maxNumber = 0;		
	boolean error = true;

	CommandLineReadable  cr = new CommandLineNewReader();;
	List<Integer> list;
	Logger logger = Logger.getLogger("orion.utility.log");	
	
	public void processFile(){
		do
		{
			SOP("Enter absolute file path and maximum integer value separated by space: ");
			try {				
				List<String> tokens = cr.readInput();								

				SOL("You have entered File Path = "+tokens.get(0));
				SOP(" and Maximum Integer="+tokens.get(1));				

				fileName = tokens.get(0);
				maxNumber = Integer.parseInt(tokens.get(1));

				writeFile(fileName, maxNumber) ;
				SOP("File Create Succesfully !");
				error=false;
				
			}catch (ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				SOP("");
				SOP("Enter both values: ");
			}catch(IOException e){
				SOP("");
				SOP("Retry: ");
			}catch(Exception e){
				SOP("Error in file creation: "+e);
			}			

		}while(error);
	}
	
	public void reverseNumbersInFile(){
		do
		{
			SOP("Enter absolute file path ");
			try {				
				List<String> tokens = cr.readInput();								

				SOL("You have entered File Path = "+tokens.get(0));								

				fileName = tokens.get(0);				

				reverseNumbersInFile(fileName) ;
				SOP("Numbers reversed in File Succesfully !");
				error=false;
				
			}catch (ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				SOP("");
				SOP("Enter file name: ");
			}catch(IOException e){
				SOP("");
				SOP("Retry: ");
			}catch(Exception e){
				SOP("Error in file creation: "+e);
			}			

		}while(error);
	}
	
	private void reverseNumbersInFile(String fileName) throws IOException {
		FileHelper fh = new FileHelper();
		List<Integer> list = fh.readToList(fileName);
		
		logger.log(Level.INFO, list.get(0).toString());
		logger.log(Level.INFO, list.get(100).toString());
		
		Descending desc = new Descending();
		list.sort(desc);
		
		logger.log(Level.INFO, list.get(0).toString());
		logger.log(Level.INFO, list.get(100).toString());		
		
		File file = new File(fileName);
		file.delete();		
		fh.writeListToFile(fileName, list);		
	}


	private void writeFile(String fileName, int maxNumber) throws IOException{
		list = new ArrayList<Integer>(maxNumber);
		for (int i = 0; i < maxNumber; i++) {
			list.add(new Integer(i));
		}
		FileHelper fhFileHelper = new FileHelper();
		fhFileHelper.writeListToFile(fileName,list);
	}

}
