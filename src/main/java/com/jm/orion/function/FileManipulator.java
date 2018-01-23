package com.jm.orion.function;

import static com.jm.orion.UserInterface.SOP;
import static com.jm.orion.UserInterface.SOL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jm.orion.exception.OrionNumberFormatException;
import com.jm.orion.file.FileHelper;
import com.jm.orion.file.NewDirectoryHelper;
import com.jm.orion.file.NewFileHelper;
import com.jm.orion.file.PrintFiles;
import com.jm.orion.reader.CommandLineNewReader;
import com.jm.orion.reader.CommandLineReadable;

public class FileManipulator {	

	String fileName ;
	String pathString ;
	int maxNumber = 0;		
	boolean error = true;

	CommandLineReadable  cr = new CommandLineNewReader();;
	List<Integer> list;
	Logger logger = Logger.getLogger("orion.utility.log");	

	public static final int REVERSE_NUMBER_IN_FILE = 1;
	public static final int MOST_REPEATED_WORD_IN_FILE = 2;
	public static final int CREATE_DIRECTORY = 3;
	public static final int CREATE_FILE = 4;

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

	public void processFile(int processCode){
		do
		{
			SOP("Enter absolute file path ");
			try {				
				List<String> tokens = cr.readInput();								

				SOL("You have entered File Path = "+tokens.get(0));								

				fileName = tokens.get(0);	

				if(processCode==1){
					reverseNumbersInFile(fileName) ;
					SOP("--------------------------------------");
					SOP("Numbers reversed in File Succesfully !");
					SOP("--------------------------------------");
				}else if(processCode==2){
					String word = findMostRepeatedWordInFile(fileName);
					SOP("--------------------------------------");
					SOP("Most repeated word is "+word);
					SOP("--------------------------------------");
				}

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

	public void createPath(int processCode){
		NewFileHelper nfh = new NewFileHelper();
		do
		{
			SOP("Enter absolute Path ");
			try {				
				List<String> tokens = cr.readInput();								

				SOP("You have entered Path = "+tokens.get(0));								

				pathString = tokens.get(0);
				Path path  = Paths.get(pathString);

				if(processCode==3){
					int code=nfh.createDirectory(path) ;					
					SOP("-------------------------------------------------------------");
					if(code==0){
						SOP("Directory created Succesfully !");
					}else if(code==2){
						SOP("Directory already exists. Please enter a different name !");
					}else{
						SOP("Error in creating Directory !");
					}
					SOP("-------------------------------------------------------------");
				}else if(processCode==4){
					int code=nfh.createFile(path) ;					
					SOP("-------------------------------------------------------------");
					if(code==0){
						SOP("File created Succesfully !");
					}else if(code==2){
						SOP("File already exists. Please enter a different name !");
					}else{
						SOP("Error in creating File !");
					}
					SOP("-------------------------------------------------------------");
				}			
				error=false;

			}catch (ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				SOP("");
				SOP("Enter Path : ");
			}catch(IOException e){
				SOP("");
				SOP("Retry: ");
			}catch(Exception e){
				SOP("Error in Object creation: "+e);
			}			

		}while(error);
	}


	public void showFileDetails(){
		NewFileHelper nfh = new NewFileHelper();
		do
		{
			SOP("Enter absolute Path ");
			try {				
				List<String> tokens = cr.readInput();								

				SOP("You have entered Path = "+tokens.get(0));								

				pathString = tokens.get(0);
				Path path  = Paths.get(pathString);


				SOP("-------------------------------------------------------------");

				System.out.format("toString: %s%n", path.toString());
				System.out.format("getFileName: %s%n", path.getFileName());
				System.out.format("getName(0): %s%n", path.getName(1));
				System.out.format("getNameCount: %d%n", path.getNameCount());
				System.out.format("subpath(0,2): %s%n", path.subpath(1,2));
				System.out.format("getParent: %s%n", path.getParent());
				System.out.format("getRoot: %s%n", path.getRoot());
				System.out.format("resolveSibling: %s%n", path.resolveSibling("dodo.xmr"));
				SOP("Regualr? "+Files.isRegularFile(path));
				SOP("Readable? "+Files.isReadable(path));
				SOP("Executable? "+Files.isExecutable(path));
				SOP("Attributes? "+Files.readAttributes(path, "*", LinkOption.NOFOLLOW_LINKS));
				
				

				SOP("-------------------------------------------------------------");

				error=false;

			}catch (ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				SOP("");
				SOP("Enter Path : ");
			}catch(IOException e){
				SOP("");
				SOP("Retry: ");
			}catch(Exception e){
				SOP("Error in Object creation: "+e);
			}			

		}while(error);
	}

	public void showDirectoryDetails(){
		NewDirectoryHelper ndh = new NewDirectoryHelper();
		do
		{
			SOP("Enter absolute Path ");
			try {				
				List<String> tokens = cr.readInput();								

				SOP("You have entered Path = "+tokens.get(0));								

				pathString = tokens.get(0);
				Path path  = Paths.get(pathString);


				SOP("-------------------------------------------------------------");

				ndh.showRootDirectories();
				ndh.showDirectoryContents(path);
				Path path1 = Paths.get("d:\\work\\EclipseError.log");
				Path link = Paths.get("d:\\zozo.txt");
				//ndh.creatLink(link, path1);
				
/*				System.out.format("toString: %s%n", path.toString());
				System.out.format("getFileName: %s%n", path.getFileName());
				System.out.format("getName(0): %s%n", path.getName(1));
				System.out.format("getNameCount: %d%n", path.getNameCount());
				System.out.format("subpath(0,2): %s%n", path.subpath(1,2));
				System.out.format("getParent: %s%n", path.getParent());
				System.out.format("getRoot: %s%n", path.getRoot());
				System.out.format("resolveSibling: %s%n", path.resolveSibling("dodo.xmr"));
				SOP("Regualr? "+Files.isRegularFile(path));
				SOP("Readable? "+Files.isReadable(path));
				SOP("Executable? "+Files.isExecutable(path));
				SOP("Attributes? "+Files.readAttributes(path, "*", LinkOption.NOFOLLOW_LINKS));*/
				
				

				SOP("-------------------------------------------------------------");

				error=false;

			}catch (ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				SOP("");
				SOP("Enter Path : ");
			}catch(IOException e){
				SOP("");
				SOP("Retry: ");
			}catch(Exception e){
				SOP("Error in Object creation: "+e);
			}			

		}while(error);
	}
	
	public void walkDirectoryTree(){
		NewDirectoryHelper ndh = new NewDirectoryHelper();
		do
		{
			SOP("Enter absolute Directory Path ");
			try {				
				List<String> tokens = cr.readInput();								

				SOP("You have entered Path = "+tokens.get(0));								

				pathString = tokens.get(0);
				Path startingDir  = Paths.get(pathString);
				PrintFiles pf = new PrintFiles();
				
				
				SOP("-------------------------------------------------------------");

				Files.walkFileTree(startingDir, pf);

				SOP("-------------------------------------------------------------");

				error=false;

			}catch (ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				SOP("");
				SOP("Enter Path : ");
			}catch(IOException e){
				SOP("");
				SOP("Retry: ");
			}catch(Exception e){
				SOP("Error in Object creation: "+e);
			}			

		}while(error);
	}
	
	
	public void splitVeryLargeFile() throws InterruptedException{
		LineNumberReader reader;
		String lineRead;
		try {
			reader = new LineNumberReader(new FileReader("d:\\EclipseError.log"));
			while ((lineRead = reader.readLine()) != null) {}			
			SOP("total lines in file: "+reader.getLineNumber());
			reader.close();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		NewFileHelper nfh = new NewFileHelper();
		int splitSize = 0;
		long fileSize=0;
		int numberOfFiles = 0;

		do
		{
			SOP("Enter Full Path of File ");
			try {				
				List<String> tokens = cr.readInput();
				SOP("You have entered Path = "+tokens.get(0));
				pathString = tokens.get(0);
				Path path  = Paths.get(pathString);

				if(Files.exists(path)){
					logger.log(Level.INFO,path.getFileName()+" exists");					
					fileSize = Files.size(path);
					SOP("File size: "+fileSize);
				}else{
					logger.log(Level.INFO,path.getFileName()+" does not exist.");
					throw new IOException("Invalid file");
				}
				SOP("Enter Split file size in KB ");
				tokens = cr.readInput();
				SOP("You have entered size = "+tokens.get(0));
				try{
					splitSize = Integer.parseInt(tokens.get(0));					
				}catch(NumberFormatException e){
					throw new OrionNumberFormatException("size should be an integer number only");
				}
				//nfh.splitBinaryFile(path , fileSize, splitSize);	
				//nfh.splitLargeBinaryFile(path , fileSize, splitSize);
				nfh.splitVeryLargeBinaryFile(path , fileSize, splitSize);
				SOP("-------------------------------------------------------------");

				error=false;

			}catch (ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				SOP("");
				SOP("Enter Path : ");
			}catch(IOException e){
				SOP("");
				SOP("Retry: ");
			}catch(Exception e){
				SOP("Error in Object creation: "+e);
			}			

		}while(error);
	}

	private void reverseNumbersInFile(String fileName) throws IOException {
		FileHelper fh = new FileHelper();
		List<Integer> list = fh.readToList(fileName);

		logger.log(Level.INFO, list.get(0).toString());
		//logger.log(Level.INFO, list.get(100).toString());

		Descending desc = new Descending();
		list.sort(desc);

		logger.log(Level.INFO, list.get(0).toString());
		//logger.log(Level.INFO, list.get(100).toString());		

		File file = new File(fileName);
		file.delete();		
		fh.writeListToFile(fileName, list);		
	}

	public String findMostRepeatedWordInFile(String fileName)throws IOException {
		FileHelper fh = new FileHelper();
		List<String> list = fh.readStringToList(fileName);

		logger.log(Level.INFO, list.get(0).toString());
		//logger.log(Level.INFO, list.get(100).toString());

		Map<String,Integer> wordMap= new HashMap<String,Integer>();
		int value = 0;
		for(String word:list){
			value=(int)(wordMap.get(word)==null?0:wordMap.get(word));
			wordMap.put(word,value+1);
		}


		Map.Entry<String,Integer> mostRepeatedEntry = null;
		for(Map.Entry<String,Integer> entry:wordMap.entrySet()){
			if(mostRepeatedEntry == null || entry.getValue()>mostRepeatedEntry.getValue()){
				mostRepeatedEntry=entry;
			}

		}

		logger.log(Level.INFO, mostRepeatedEntry.getKey());
		//logger.log(Level.INFO, list.get(100).toString());		

		return mostRepeatedEntry.getKey();

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
