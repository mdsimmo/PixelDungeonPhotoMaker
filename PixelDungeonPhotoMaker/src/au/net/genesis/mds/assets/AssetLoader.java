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
		System.out.println("loading image: (" + file + ")\n");
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
		/*URL url = assetLoader.getClass().getResource("/resources/" + image);
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
		*/
	}
	
	/**
	 * Gives the file to a game's asset
	 * 
	 * @param file the name of the asset
	 * @return the file of the asset
	 */
	public static String getDungeonFile(String file) {
		return getImagePath("gameimages/" + file);
	}

}
