package com.jm.orion.function;

import java.util.Comparator;

public class Descending implements Comparator<Integer>{
	
    public int compare(Integer x, Integer y){
        
    	if(x<y){
            return 1;
        }
        if(x>y){
            return -1;
        }
        return 0;
    }
}
