package au.net.genesis.mds.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class AssetLoader {
	
	public static AssetLoader assetLoader = new AssetLoader();
	
	/**
	 * loads the specified image file
	 * 
	 * @param string
	 *            the location inside the resource directory
	 * @return the resource
	 */
	public static BufferedImage loadImage(String file) {
		InputStream input = AssetLoader.class.getResourceAsStream(file);
		try {
			return ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * gets the file of an image
	 * @param image the image's file's name
	 * @return the file of the image
	 */
	public static String getImagePath(String image) {
		return "/resources/" + image;
	}
	
	/**
	 * Gives the file to a game's asset
	 * 
	 * @param file the name of the asset
	 * @return the file of the asset
	 */
	public static String getDungeonPath(String file) {
		return getImagePath("gameimages/" + file);
	}

}
