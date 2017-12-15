package com.jm.orion.function;

import static com.jm.orion.UserInterface.SOP;
import static com.jm.orion.UserInterface.SOL;

import java.io.IOException;
import java.util.List;

import com.jm.orion.exception.LowValueException;
import com.jm.orion.exception.OrionNumberFormatException;
import com.jm.orion.reader.CommandLineNewReader;
import com.jm.orion.reader.CommandLineReadable;
import com.jm.orion.reader.CommandLineReader;

public class NumberManipulator {

	public void addNumberfromCommandLine(){
		int a = 0;
		int b = 0;		
		boolean error = true;

		//CommandLineReadable cr = new CommandLineReader();
		CommandLineReadable cr = new CommandLineNewReader();	

		do{
			SOP("Enter two numeric values separated by space: ");
			try {				
				List<String> tokens = cr.readInput();								

				SOL("You have entered A="+tokens.get(0));
				SOL(" and B="+tokens.get(1));				

				try{
					a = Integer.parseInt(tokens.get(0));
					b = Integer.parseInt(tokens.get(1));
				}catch(NumberFormatException e){
					throw new OrionNumberFormatException("Both values should be number only");
				}

				add(a, b);
				error=false;

			}catch(LowValueException|OrionNumberFormatException e){ // **Multi value Exception **
				SOP("");
				//e.printStackTrace();
				SOP(e.getMessage());
			/*}catch(OrionNumberFormatException e){				
				//SOP("");
				//SOP("Both values should be number only");
				SOP(e.getMessage());*/
			}catch (ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				SOP("");
				SOP("Enter both values: ");
			}catch(IOException e){
				SOP("");
				SOP("Retry: ");
			}

		}while(error);

	}
	
	private void add(int a, int b) throws LowValueException
	{
		if(a<10)
			throw new LowValueException("Input value is too low");			
		if(b<10)
			throw new LowValueException("Input value is too low");
		SOP("");
		SOP("Total is: "+(a+b));
	}
	

}
