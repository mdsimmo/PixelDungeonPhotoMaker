package au.net.genesis.mds.assets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import au.net.genesis.mds.PhotoMaker;

public enum WellType {
	MAGIC_WELL("well.png"), ALCHEMY("pot.png");

	private String file;
	private BufferedImage texture;

	WellType(String file) {
		this.file = file;
		try {
			texture = ImageIO.read(new File(getFileName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	String getFileName() {
		return PhotoMaker.getResource(file);
	}

	public void drawImage(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, null);
	}

	public BufferedImage getImage() {
		return texture;
	}

	public int getHeight() {
		return texture.getHeight();
	}

	public int getWidth() {
		return texture.getWidth();
	}
}
