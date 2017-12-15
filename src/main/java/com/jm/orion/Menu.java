package com.jm.orion;

import static com.jm.orion.UserInterface.SOP;

public class Menu {

	
	public void showWelcomeMenu(){
		SOP("--------------------------------\n");
		SOP("Welcome to Orion Utility \n");
		SOP("--------------------------------\n");		
	}
	
	public void showMainMenu(){
		SOP("************************");
		SOP("Choose an option...");
		SOP("1- Add Two Numbers");
		SOP("2- Anagrams");
		SOP("3- Write to A File");		
		SOP("4- Zip File");
		SOP("5- Reverse numbers in File");
		SOP("6- Do Nothing");		
		SOP("0- Exit");
		SOP("************************");
	}
}
