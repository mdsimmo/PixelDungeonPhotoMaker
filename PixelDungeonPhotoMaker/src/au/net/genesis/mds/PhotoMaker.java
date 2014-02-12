package au.net.genesis.mds;

import au.net.genesis.mds.imageEditors.ParticleScene;

public class PhotoMaker {

	public static void main(String[] args) {

		ParticleScene ps = new ParticleScene();
		ps.setSceneSize(96, 128)
			.set;
		ps.begin();

	}

	/**
	 * Gets the path to the resources <br>
	 * (mainly used so I can change where resources are stored)
	 * 
	 * @param string
	 *            the location inside the resource directory
	 * @return the resource path
	 */
	public static String getResource(String string) {
		return "resources/" + string;

	}

}
