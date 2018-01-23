package com.jm.orion.file;

import static com.jm.orion.UserInterface.SOP;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewFileHelper {

	Logger logger = Logger.getLogger("orion.utility.log");	

	public int createDirectory(Path dirPath){
		if(!Files.exists(dirPath)){
			try {
				Files.createDirectory(dirPath);
				return 0;
			}catch(FileAlreadyExistsException e){
				return  2;
			}catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return  1;
			}
		}
		logger.log(Level.SEVERE, "Directory Could not be created: ");
		return  1;

	}

	public int createFile(Path filePath){		
		if(!Files.exists(filePath)){
			try {
				Files.createFile(filePath);
				return 0;
			}catch(FileAlreadyExistsException e){
				return  2;
			}catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return  1;
			}
		}
		logger.log(Level.SEVERE, "File Could not be created: ");
		return  1;

	}

	public void splitBinaryFile(Path path , long fileSize, int splitSize) throws IOException{			
		long startTime = System.currentTimeMillis();
		int dotIndex = path.getFileName().toString().lastIndexOf(".");
		String fileName = path.getFileName().toString().substring(0, dotIndex);
		String extension = path.getFileName().toString().substring(dotIndex);
		//SOP("fileName without extension: "+fileName);

		int numberOfFiles = (int)fileSize/(splitSize*1024)+1;
		SOP(numberOfFiles);
		Path[] splitPath = new Path[numberOfFiles];
		byte[] fileData = Files.readAllBytes(path);
		SOP("array length<splitBinaryFile>; "+fileData.length);

		for(int i = 0;i<numberOfFiles;i++){						
			splitPath[i]
					= Paths.get(path.normalize().getParent().toString(),fileName+i+extension) ;
			//SOP("path created..");
			byte[] partData = Arrays.copyOfRange(fileData, i*splitSize*1024, ((i+1)*splitSize*1024)>fileData.length?fileData.length:((i+1)*splitSize*1024));
			//SOP("byte array created.."+partData.length);
			Files.deleteIfExists(splitPath[i]);
			Files.createFile(splitPath[i]);
			//SOP("file created..");
			SOP(splitPath[i]);	

			try (OutputStream os = Files.newOutputStream(splitPath[i])) {
				//SOP("writing to file...."+partData.length+" : "+Math.ceil(partData.length/(splitSize*1024/10.0)));
				for(int j =0;j<Math.ceil(partData.length/(splitSize*1024/10.0)); j++){
					//SOP("value of j: "+j);
					os.write(partData,j*splitSize*1024/10,splitSize*1024/10*(j+1)>partData.length?partData.length-(splitSize*1024/10*j):splitSize*1024/10);
				}
				//os.write(partData);
			} catch (IOException x) {
				System.err.format("IOException: %s%n", x);
			}		
			long endTime = System.currentTimeMillis();
			System.out.println("Image blur took " + (endTime - startTime) + 
					" milliseconds.");
		}		
	}

	public void splitLargeBinaryFile(Path path , long fileSize, int splitSize) throws IOException{			
		long startTime = System.currentTimeMillis();
		int dotIndex = path.getFileName().toString().lastIndexOf(".");
		String fileName = path.getFileName().toString().substring(0, dotIndex);
		String extension = path.getFileName().toString().substring(dotIndex);
		//SOP("fileName without extension: "+fileName);
		int numberOfFiles = (int)fileSize/(splitSize*1024)+1;
		//SOP(numberOfFiles);
		Path[] splitPath = new Path[numberOfFiles];		

		//reading from a FileInput Stream, writing to ByteArrayOutputStream and then to byte[] is slow
		//SeekableByteChannel is faster
		SeekableByteChannel sbc = Files.newByteChannel(path, StandardOpenOption.READ);
		ByteBuffer byteBuffer = ByteBuffer.allocate(splitSize*1024);		

		SeekableByteChannel[] sbcWrite = new SeekableByteChannel[numberOfFiles];

		for(int i = 0;i<numberOfFiles;i++){					
			splitPath[i]
					= Paths.get(path.normalize().getParent().toString(),fileName+i+extension) ;		
			SOP(splitPath[i]);	
			try{	
				sbcWrite[i] =Files.newByteChannel(splitPath[i], StandardOpenOption.CREATE, StandardOpenOption.WRITE);
				SOP(sbcWrite[i].position());
				
				byteBuffer.clear();
				SOP("remaining1: "+byteBuffer.remaining());					
				sbc.read(byteBuffer);
				SOP("remaining2: "+byteBuffer.remaining());
				if(byteBuffer.remaining()>0){	
					SOP("in remaining>0");
					ByteBuffer lastBuffer = ByteBuffer.allocate(byteBuffer.capacity()-byteBuffer.remaining());
					byteBuffer.flip();
					lastBuffer.put(byteBuffer);
					lastBuffer.rewind();					
					SOP("postion of lastBuffer: "+lastBuffer.position());
					SOP("remaining of lastBuffer: "+lastBuffer.remaining());
					sbcWrite[i].write(lastBuffer);
				}else{			
					byteBuffer.rewind();
					SOP("remaining3: "+byteBuffer.remaining());
					sbc.position((i+1)*splitSize*1024>fileSize?fileSize:(i+1)*splitSize*1024);
					SOP("channel position: "+sbc.position());
					sbcWrite[i].write(byteBuffer);
				}
				SOP(sbcWrite[i].position());
			} catch (IOException x) {
				System.out.println("Exception thrown: " + x);
			}finally{
				sbcWrite[i].close();
			}				
			long endTime = System.currentTimeMillis();
			System.out.println("splitLargeBinaryFile() took " + (endTime - startTime) + 
					" milliseconds.");
		}		
		sbc.close();
	}
	
	public void splitVeryLargeBinaryFile(Path path , long fileSize, int splitSize) throws IOException{			
		long startTime = System.currentTimeMillis();
		int dotIndex = path.getFileName().toString().lastIndexOf(".");
		String fileName = path.getFileName().toString().substring(0, dotIndex);
		String extension = path.getFileName().toString().substring(dotIndex);
		//SOP("fileName without extension: "+fileName);
		int numberOfFiles = (int)fileSize/(splitSize*1024)+1;
		//SOP(numberOfFiles);
		Path[] splitPath = new Path[numberOfFiles];		

		//reading from a FileInput Stream, writing to ByteArrayOutputStream and then to byte[] is slow
		//SeekableByteChannel is faster
		FileChannel fc = FileChannel.open(path, StandardOpenOption.READ);		
		ByteBuffer byteBuffer = ByteBuffer.allocate(splitSize*1024);		

		FileChannel[] fcWrite = new FileChannel[numberOfFiles];

		for(int i = 0;i<numberOfFiles;i++){					
			splitPath[i]
					= Paths.get(path.normalize().getParent().toString(),fileName+i+extension) ;		
			SOP(splitPath[i]);	
			try{	
				fcWrite[i] =FileChannel.open(splitPath[i], StandardOpenOption.CREATE, StandardOpenOption.WRITE);
				SOP(fcWrite[i].position());
										
				byteBuffer.clear();
				SOP("remaining1: "+byteBuffer.remaining());					
				fc.read(byteBuffer);
				SOP("remaining2: "+byteBuffer.remaining());
				if(byteBuffer.remaining()>0){	
					SOP("in remaining>0");
					ByteBuffer lastBuffer = ByteBuffer.allocate(byteBuffer.capacity()-byteBuffer.remaining());
					byteBuffer.flip();					
					lastBuffer.put(byteBuffer);
					lastBuffer.rewind();					
					SOP("postion of lastBuffer: "+lastBuffer.position());
					SOP("remaining of lastBuffer: "+lastBuffer.remaining());
					fcWrite[i].write(lastBuffer);
				}else{			
					byteBuffer.rewind();
					SOP("remaining3: "+byteBuffer.remaining());
					fc.position((i+1)*splitSize*1024>fileSize?fileSize:(i+1)*splitSize*1024);
					SOP("channel position: "+fc.position());
					fcWrite[i].write(byteBuffer);
				}
				SOP(fcWrite[i].position());
			} catch (IOException x) {
				System.out.println("Exception thrown: " + x);
			}finally{
				fcWrite[i].close();
			}				
			long endTime = System.currentTimeMillis();
			System.out.println("splitVeryLargeBinaryFile() took " + (endTime - startTime) + 
					" milliseconds.");
		}		
		fc.close();
	}
}


