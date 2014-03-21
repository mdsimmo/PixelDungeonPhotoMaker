package au.net.genesis.mds.assets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;


public enum WellType {
	MAGIC_WELL("well.png"), ALCHEMY("pot.png");

	private BufferedImage texture;
	private File f;

	WellType(String file) {
		f = AssetFinder.getImageFile(file);
		texture = AssetFinder.loadImage(f);
		
	}

	public void drawImage(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, null);
	}

	public BufferedImage getImage() {
		return texture;
	}
	
	public File getFile() {
		return f;
	}

	public int getHeight() {
		return texture.getHeight();
	}

	public int getWidth() {
		return texture.getWidth();
	}
}
