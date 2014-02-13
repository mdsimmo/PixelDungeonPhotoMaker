package au.net.genesis.mds;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import au.net.genesis.mds.helpers.GifSequenceWriter;
import au.net.genesis.mds.imageEditors.ParticleScene;

public class PhotoMaker {

	/*
	 * TODO list: create own image drop shadow [optional] make an auto gif
	 * creator make enemy gifs make standard images gui version?
	 */

	public static void main(String[] args) {
		//ParticleScene ps = new ParticleScene();
		//ps.begin();
		
		try {
			File inputDir = new File("output/");
			File[] inputImages = inputDir.listFiles();
			ImageOutputStream output = new FileImageOutputStream(new File(
					"output2/gif.gif"));
			GifSequenceWriter writer = new GifSequenceWriter(output,
					BufferedImage.TYPE_INT_ARGB, 5, true);

			for (int i = 0; i < inputImages.length; i++) {
				writer.writeToSequence(ImageIO.read(inputImages[i]));
			}
			writer.close();
			output.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
