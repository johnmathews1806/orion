package com.jm.orion.file;

import static com.jm.orion.UserInterface.SOP;

import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class FileHelper {
	
	private static final String CREDENTIAL_STORE= "D:\\MyProject\\orion\\credentials.txt";


	public void writeListToFile(String fileName, List list) throws IOException {

		PrintWriter out = new PrintWriter(new FileWriter(fileName));

		for (int i = 0; i < list.size(); i++) {
			// The get(int) method throws IndexOutOfBoundsException, which must be caught.
			//out.println("Value at: " + i + " = " + list.get(i));
			out.print(list.get(i)+",");
		}
		out.close();

	}

	public byte[] read(File file) //throws IOException, FileTooBigException 
	{
		/*
		 * if (file.length() > MAX_FILE_SIZE) {
	        throw new FileTooBigException(file);
	    }
		 */
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}

			ous.close();
			ios.close();
		}catch(FileNotFoundException e){
			SOP(e.getMessage());			
		}catch(IOException e){
			SOP(e.getMessage());
		}
		return ous.toByteArray();
	}



	public byte[] readToBytes(File file) //throws IOException, FileTooBigException 
	{
		/*
		 * if (file.length() > MAX_FILE_SIZE) {
	        throw new FileTooBigException(file);
	    }
		 */
				
		InputStream ios = null;
		long length = file.length();
		byte[] buffer = new byte[(int) length]; 
		try {					
			ios = new FileInputStream(file);			
			if (ios.read(buffer) == -1) {
				ios.close();
				throw new IOException("EOF reached while trying to read the whole file");
			}
		}catch(FileNotFoundException e){
			SOP(e.getMessage());			
		}catch(IOException e){			
			SOP(e.getMessage());
		}
		return buffer;
	}

	public List<Integer> readToList(String fileName) throws IOException{
		List<Integer> list = new ArrayList<Integer>();				
		Scanner sc = new Scanner(new File(fileName)).useDelimiter(",");		
		int value;
		while(sc.hasNextInt()){
			value = sc.nextInt();
			//SOP("FileHelper: "+value);
			list.add(value);
		}		
		return list;
	}
	
	public List<String> readStringToList(String fileName) throws IOException{
		List<String> list = new ArrayList<String>();				
		Scanner sc = new Scanner(new File(fileName));//.useDelimiter(" ");		
		String value;
		while(sc.hasNext()){
			value = sc.next();
			//SOP("FileHelper: "+value);
			list.add(value);
		}		
		return list;
	}
	
	public void storeCredentials(String username, String password){
		//PrintWriter os = null;
		DataOutputStream os  = null;
		
		try {			
			//os = new PrintWriter(new FileWriter(CREDENTIAL_STORE,true));			
			os = new DataOutputStream(new FileOutputStream(new File(CREDENTIAL_STORE),true));
			//os.write(username+"="+password+";");
			os.writeChars(username+"="+password+";");			
		} catch (FileNotFoundException e1) {			
			SOP("Incorrect File Path. Exiting");
		}catch(IOException e2){
			SOP("Error in writing to credential store"+e2);
		}finally{	
			try{
				os.close();
			}catch (Exception e3) {
				SOP("error in closing stream"+e3);
			}
		}
	}
	
	public Map getCredentialStore(){
		DataInputStream dis =null;	
		
		StringBuffer credStringBuffer= new StringBuffer();		
		try {
			dis = new DataInputStream(new BufferedInputStream(new FileInputStream(CREDENTIAL_STORE)));			
			
			while(true){
				credStringBuffer.append(dis.readChar());				
			}			
		} catch (FileNotFoundException e1) {
			SOP("Incorrect File Path. Exiting");
		} catch (EOFException ex) {
			SOP("End of credential file");
			try{
				dis.close();
			}catch(IOException e2){
				SOP("error in closing stream"+e2);
			}
	    }catch(IOException e3){
			SOP("Error in reading from credential store"+e3);
		}
		
		//SOP(credStringBuffer);
		Map<String,String> credentialStore =new HashMap<String,String>();
		String credString = credStringBuffer.toString();
		String[] credStringArray=credString.split("(;|=)");
		SOP("String Array: "+credStringArray[0]+", "+credStringArray[1]);
		for (int i=0; i<credStringArray.length;i++){			
			credentialStore.put(credStringArray[i], credStringArray[i+1]);
			i++;
		}
	
		return credentialStore;
	}
	
	public void serializeObject(Object obj){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("d:/WORK/object.tmp"));
			oos.writeObject(obj);
			oos.close();
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}
	

}
