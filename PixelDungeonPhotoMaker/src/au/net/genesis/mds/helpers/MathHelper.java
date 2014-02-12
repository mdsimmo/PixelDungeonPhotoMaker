package au.net.genesis.mds.helpers;

public class MathHelper {

	
	/**
	 *  returns random number between fist (inclusive) and second (exclusive)
	 */
	public static double randomRange(double first, double second) {
		return Math.random()*(second-first) + first;		
	}
	/**
	 *  returns random number between fist (inclusive) and second (exclusive)
	 */
	public static float randomRange(float first, float second) {
		return (float)randomRange((double)first, (double)second);		
	}
	/**
	 *  returns random number between fist (inclusive) and second (exclusive)
	 */
	public static int randomRange(int first, int second) {
		return (int)randomRange((double)first, (double)second);		
	}
	
	/**
	 *  returns random number between fist (inclusive) and second (inclusive)
	 */
	public static double randomRangeInclusive(double first, double second) {
		return randomRange(first, second+1);		
	}
	/**
	 *  returns random number between fist (inclusive) and second (inclusive)
	 */
	public static float randomRangeInclusive(float first, float second) {
		return (float)randomRange((double)first, (double)(second+1));		
	}
	/**
	 *  returns random number between fist (inclusive) and second (inclusive)
	 */
	public static int randomRangeInclusive(int first, int second) {
		return (int)randomRange((double)first, (double)(second+1));		
	}
	
	/**
	 *  returns whether the specified side lengths (i,j,k) can form a perfect right-angle triangle 
	 * @param i any side
	 * @param j any side
	 * @param k any side
	 */
	public static boolean isPythagTriad(int i, int j, int k) {
		return (isOrderedPythagTriad(i, j, k) || isOrderedPythagTriad(k, i, j) || isOrderedPythagTriad(
				j, k, i));
	}
	
	/**
	 *  returns whether the specified side lengths (a,b,c) form a perfect right-angle triangle 
	 * @param a hypotenuse
	 * @param b adjacent
	 * @param c opposite
	 */
	private static boolean isOrderedPythagTriad(int a, int b, int c) {
		return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)) == (double) c;
	}
		
	
	/**
	 * gets the x component of a vector. Direction is in degrees taken anti-clockwise with 0deg to be to the right  
	 * @param magnitude the vectors magnitude
	 * @param degrees the vectors direction 
	 * @return the x component of the vector
	 */
	public static double xcomponent(double magnitude, double degrees) {
		return magnitude * Math.cos(degToRad(degrees));
	}

	/**
	 * gets the y component of a vector. Direction is in degrees taken anti-clockwise with 0deg to be to the right  
	 * @param magnitude the vectors magnitude
	 * @param degrees the vectors direction 
	 * @return the y component of the vector
	 */
	public static double ycomponent(double magnitude, double degrees) {
		return magnitude * Math.sin(degToRad(degrees));
	}

	
	public static double radToDeg(double radians) {
		return radians * 180 / Math.PI;
	}
	public static double degToRad(double degrees) {
		return degrees * Math.PI /180;
	}
	
}
