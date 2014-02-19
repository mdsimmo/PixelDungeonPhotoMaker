package au.net.genesis.mds.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AssetLoader {
	
	/**
	 * loads an image in the resources file
	 * 
	 * @param string
	 *            the location inside the resource directory
	 * @return the resource
	 */
	public static BufferedImage loadImage(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("could not get image: " + file);
			return null;
		}
	}
	
	public static File getImageFile(String image) {
		//System.out.println(PhotoMaker.class.getClassLoader().getResource("resources/" + image).toString());
		//URL url = ClassLoader.class.getResource("C:\\Documents and Settings\\Simmons\\My Documents\\Matthew");
		//System.out.println(url);
		File file = null;
		//file = new File( URLDecoder.decode( url.getFile(), "UTF-8" ));
		file = new File("bubble.png");
		//File file =  new File("/resources/" + image);
		System.out.println(file.getAbsolutePath());
		return file;
	}
	
	/**
	 * Gives the relative path to the games assets folder
	 * 
	 * @param file
	 * @return
	 */
	public static File getDungeonFile(String file) {
		return getImageFile("gameimages/" + file);
	}

}
