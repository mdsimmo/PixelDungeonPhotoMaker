package au.net.genesis.mds.assets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public enum WellType {
	MAGIC_WELL("well.png"), ALCHEMY("pot.png");

	private BufferedImage texture;

	WellType(String file) {
		texture = AssetLoader.loadImage(AssetLoader.getImageFile(file));
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
