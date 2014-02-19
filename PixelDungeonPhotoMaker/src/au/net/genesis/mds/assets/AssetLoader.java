package au.net.genesis.mds.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
	public static BufferedImage loadImage(File file) {
		//InputStream input = AssetLoader.class.getResourceAsStream(file);
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * gets the file of an image
	 * @param image the file's name
	 * @return the file
	 */
	public static File getImageFile(String image) {
		return new File("resources/" + image);
	}
	
	/**
	 * Gives the file to a game's asset
	 * 
	 * @param file the game of the asset
	 * @return the file of the asset
	 */
	public static File getDungeonFile(String file) {
		return getImageFile("gameassets/" + file);
	}

	public static File getTempFile(String file) {
		return getImageFile("temp/" + file);
	}

}
