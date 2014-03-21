package au.net.genesis.mds.assets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;


public enum InfoboxBack {
	
	/** 
	 * Infobox background used for the Sewers levels
	 */
	SEWER("infoboxsewers.png"),
	/** 
	 * Infobox background used for the Prison levels
	 */
	PRISON("infoboxprison.png"),
	/** 
	 * Infobox background used for the Caves levels
	 */
	CAVES("infoboxcaves.png"),
	/** 
	 * Infobox background used for the Dwarf City
	 */
	CITY("infoboxcity.png"),
	/** 
	 * Infobox background used for the Demon Halls
	 */
	HALLS("infoboxhalls.png");
	
	private BufferedImage texture;
	private File file;
	public static final int BACKGROUND_SIZE = 256;
	
	InfoboxBack(String fileName) {
		this.file = AssetFinder.getImageFile(fileName);
		texture = AssetFinder.loadImage(file);
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
		g.drawImage(texture, x, y, null);
		return true;
	}

	/**
	 * gets the location where the infobox's image is stored
	 * @return
	 */
	public File getImageFile() {
		return file;
	}
}
