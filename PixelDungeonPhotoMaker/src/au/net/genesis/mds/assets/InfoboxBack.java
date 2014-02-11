package au.net.genesis.mds.assets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import au.net.genesis.mds.PhotoMaker;

public enum InfoboxBack {
	
	/** 
	 * Infobox background used for the Sewers
	 */
	//TODO SEWER("sewersinfobox.png"),
	/** 
	 * Infobox background used for the Prison
	 */
	//TODO PRISON("prisoninfobox.png"),
	/** 
	 * Infobox background used for the Caves
	 */
	//TODO CAVES("cavesinfobox.png"),
	/** 
	 * Infobox background used for the Dwarf City
	 */
	//TODO CITY("cityinfobox.png"),
	/** 
	 * Infobox background used for the Demon Halls
	 */
	HALLS("hallsinfobox.png");
	
	private String fileName;
	public static final int BACKGROUND_SIZE = 256;
	
	InfoboxBack(String fileName) {
		this.fileName = PhotoMaker.getResource(fileName);
	}
	
	/**
	 * Draws the image onto the specified graphics object
	 * 
	 * @param g the Graphics object to draw onto
	 * @param x the x location to draw at
	 * @param y the y location to draw at
	 * @return true if the image was drawn, false otherwise
	 */
	public boolean drawImage(Graphics g, int x, int y) {
		BufferedImage texture;
		try {
			texture = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.err.println("InfoboxBack: could not red image " + fileName);
			return false;
		}
		g.drawImage(texture, x, y, null);
		return true;
	}

}
