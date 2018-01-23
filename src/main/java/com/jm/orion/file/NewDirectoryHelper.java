package com.jm.orion.file;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class NewDirectoryHelper {
	
	Logger logger = Logger.getLogger("orion.utility.log");
	
	public void showRootDirectories(){
		Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
		for (Path name: dirs) {
		    System.out.println(name);
		}
	}
	
	public void showDirectoryContents(Path dir){
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
		    for (Path file: stream) {
		        System.out.println(file.getFileName());
		    }
		} catch (IOException | DirectoryIteratorException x) {
		    // IOException can never be thrown by the iteration.
		    // In this snippet, it can only be thrown by newDirectoryStream.
		    System.err.println(x);
		}
	}
	
	public void creatLink(Path link, Path path){
		try {
		    Files.createSymbolicLink(link, path);
		} catch (IOException x) {
		    System.err.println(x);
		} catch (UnsupportedOperationException x) {
		    // Some file systems do not support symbolic links.
		    System.err.println(x);
		}
		
	}
}
