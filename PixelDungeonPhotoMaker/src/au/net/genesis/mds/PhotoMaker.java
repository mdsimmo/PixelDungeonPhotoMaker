package au.net.genesis.mds;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import au.net.genesis.mds.assets.InfoboxBack;
import au.net.genesis.mds.imageEditors.InfoboxCreator;

public class PhotoMaker {

	/*
	 * TODO list:
	 * create own image drop shadow [optional] 
	 * make an auto gif creator
	 * make enemy gifs
	 * make standard images
	 * gui version?
	 */
	
	public static void main(String[] args) {

		InfoboxCreator ic = new InfoboxCreator();
		ic.setAsset("assets/rat.png")
			.setBackground(InfoboxBack.SEWER)
			.setSelection(new Rectangle(16, 16))
			.setItemScale(14);

		try {
			ImageIO.write(ic.getImage(), "png", new File("output2/save.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
