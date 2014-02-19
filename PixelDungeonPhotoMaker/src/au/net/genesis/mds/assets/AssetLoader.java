package au.net.genesis.mds.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AssetLoader {
	
	/**
	 * loads the specified image file
	 * 
	 * @param string
	 *            the location inside the resource directory
	 * @return the resource
	 */
	public static BufferedImage loadImage(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("could not load image: " + file);
			return null;
		}
	}
	
	/**
	 * gets the file of an image
	 * @param image the image's file's name
	 * @return the file of the image
	 */
	public static File getImageFile(String image) {
		File file = new File("resources/" + image);
		System.out.println(file.getAbsolutePath());
		return file;
	}
	
	/**
	 * Gives the file to a game's asset
	 * 
	 * @param file the name of the asset
	 * @return the file of the asset
	 */
	public static File getDungeonFile(String file) {
		return getImageFile("gameimages/" + file);
	}

}
