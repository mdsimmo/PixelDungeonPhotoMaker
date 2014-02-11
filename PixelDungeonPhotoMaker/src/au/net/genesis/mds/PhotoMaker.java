package au.net.genesis.mds;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import au.net.genesis.mds.assets.InfoboxBack;
import au.net.genesis.mds.imageEditors.ItemCreator;

public class PhotoMaker {

	public static void main(String[] args) {
		ItemCreator ic = new ItemCreator();
		ic.setAsset(getResource("assets/items.png"))
			.setBackground(InfoboxBack.HALLS);
		try {
			ImageIO.write(ic.getImage(), "png", new File(
					getResource("saved.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Gets the path to the resources <br>
	 * (mainly used so I can change where resources are stored)
	 * 
	 * @param string the location inside the resource directory
	 * @return the resource path
	 */
	public static String getResource(String string) {
		return "resources/" + string;

	}

}
