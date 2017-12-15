package com.jm.orion.function;

import static com.jm.orion.UserInterface.SOP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.jm.orion.file.FileHelper;
import com.jm.orion.reader.CommandLineReadable;
import com.jm.orion.reader.CommandLineReader;

public class Zipper {

	CommandLineReadable cr = new CommandLineReader();
	//CommandLineReadable cr = new CommandLineNewReader();	

	public void zipFile() throws IOException{

		boolean error = true;
		String fileName = null;
		String zipName = null;
		do{
			try{
				SOP("Enter name of file to be zipped");
				fileName = cr.readInput().get(0);
				SOP("Enter name of zip file");
				zipName = cr.readInput().get(0);
				error=false;
			}catch(IOException e){
				SOP("");
				SOP("Retry: ");
			}
		}while(error);

		File f = new File(zipName);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
		File inputFile = new File(fileName);
		//SOP(tempFile.getName());
		ZipEntry e = new ZipEntry(inputFile.getName());		
		out.putNextEntry(e);

		FileHelper fh = new FileHelper();
		byte[] fileData = fh.readToBytes(inputFile);		

		//String str = "Newly added text";
		//byte[] data = str.getBytes();

		out.write(fileData, 0, fileData.length);
		out.closeEntry();

		out.close();
	}

}
