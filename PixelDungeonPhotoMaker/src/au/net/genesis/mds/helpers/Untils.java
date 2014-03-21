package au.net.genesis.mds.helpers;

public class Untils {

	public static String fromatInt(int number, int didgits) {
		String formated = Integer.toString(number);
		for (int i = formated.length(); i < didgits; i ++) {
			formated = "0" + formated;
		}
		return formated;
	}
	
}
