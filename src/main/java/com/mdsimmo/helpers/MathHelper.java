package com.mdsimmo.helpers;

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
		return (float)randomRange(first, (double)second);
	}
	/**
	 *  returns random number between fist (inclusive) and second (exclusive)
	 */
	public static int randomRange(int first, int second) {
		return (int)randomRange((double)first, second);
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
		return (float)randomRange((double)first, second+1);
	}
	/**
	 *  returns random number between fist (inclusive) and second (inclusive)
	 */
	public static int randomRangeInclusive(int first, int second) {
		return (int)randomRange((double)first, second+1);
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
