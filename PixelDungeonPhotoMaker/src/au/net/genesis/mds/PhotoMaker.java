package au.net.genesis.mds;

import au.net.genesis.mds.imageEditors.ParticleScene;

public class PhotoMaker {

	/*
	 * TODO list:
	 * create other infobox images
	 * create own image drop shadow [optional] 
	 * auto create gifs
	 * gui version?
	 */
	
	public static void main(String[] args) {

		ParticleScene ps = new ParticleScene();
		ps.begin();

	}

	/**
	 * Gets the path to the resource folder
	 * 
	 * @param string
	 *            the location inside the resource directory
	 * @return the resource file's path
	 */
	public static String getResource(String string) {
		return "resources/" + string;

	}

}
