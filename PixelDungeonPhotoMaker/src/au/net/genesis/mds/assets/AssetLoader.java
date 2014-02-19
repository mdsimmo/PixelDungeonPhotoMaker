package au.net.genesis.mds.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

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
		System.out.println("loading image: (" + file + ")\n");
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("could not load image: (" + file + ")\n");
			return null;
		}
	}
	
	/**
	 * gets the file of an image
	 * @param image the image's file's name
	 * @return the file of the image
	 */
	public static File getImageFile(String image) {
		URL url = assetLoader.getClass().getResource("/resources/" + image);
		if (url == null) {
			System.out.println("troublesome image: " + image);
		}
		File file = null;
		try {
			file = new File(URLDecoder.decode(url.getPath(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("file path: (" + file.getPath() + ")\n");
		//File file = new File("resources/" + image);
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
