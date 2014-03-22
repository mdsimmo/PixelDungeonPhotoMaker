package au.net.genesis.mds.helpers;

import java.io.File;

public class Untils {

	public static String fromatInt(int number, int didgits) {
		String formated = Integer.toString(number);
		for (int i = formated.length(); i < didgits; i ++) {
			formated = "0" + formated;
		}
		return formated;
	}
	
	public static boolean deleteDirectory(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}
}
