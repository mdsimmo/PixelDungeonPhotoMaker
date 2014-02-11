package au.net.genesis.mds.imageEditors;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import au.net.genesis.mds.assets.InfoboxBack;
import au.net.genesis.mds.helpers.GraphicHelper;

public class ItemCreator {

	private BufferedImage asset = null;
	private Rectangle selection;
	private InfoboxBack background = null;
	private int itemScale = 10;

	/**
	 * Takes an item's image and creates an image for an infobox
	 */
	public ItemCreator() {
		selection = new Rectangle(0, 0, 16, 16);
	}

	/**
	 * Sets the file to take the asset from.
	 * 
	 * @param fileName
	 *            the name of the asset file
	 * @return this
	 */
	public ItemCreator setAsset(String fileName) {
		try {
			asset = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.out.printf("Could not load image %s", fileName);
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Sets the selection for the assets image. <br>
	 * TODO Any white space is around the selection is automatically striped
	 * away.
	 * 
	 * @param selection
	 *            the rectangle containing only the item's image
	 * @return this
	 */
	public ItemCreator setSelection(Rectangle selection) {
		this.selection = selection;
		return this;
	}

	/**
	 * Sets the infobox's background
	 * 
	 * @param background
	 *            the background to use
	 * @return this
	 */
	public ItemCreator setBackground(InfoboxBack background) {
		this.background = background;
		return this;
	}

	
	/**
	 * Gets the final infobox image
	 * 
	 * @return the infobox's image
	 */
	public BufferedImage getImage() {
		if (asset != null && background != null && selection != null) {
			// Create the background (which is already correct size)
			BufferedImage image = new BufferedImage(
					InfoboxBack.BACKGROUND_SIZE, InfoboxBack.BACKGROUND_SIZE,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.getGraphics();
			background.drawImage(g, 0, 0);

			// gets the asset's image
			BufferedImage itemImage = new BufferedImage(selection.width,
					selection.height, BufferedImage.TYPE_INT_ARGB);
			Graphics ig = itemImage.getGraphics();
			ig.drawImage(asset, -selection.x, -selection.y, null);
			
			// scale the item by factor of itemScale
			itemImage = GraphicHelper.scaleImage(itemImage, itemScale, itemScale);

			// 
			g.drawImage(itemImage,
					(InfoboxBack.BACKGROUND_SIZE - itemImage.getWidth()) / 2,
					(InfoboxBack.BACKGROUND_SIZE - itemImage.getHeight()) / 2, null);
			return image;
		} else {
			System.err
					.println("Infobox could not draw: not all has information initialised!");
			return null;
		}
	}
	
	/**
	 * Set the factor that the item should be scaled up by. <br>
	 * Defaults to 10
	 * @param scale the scale. To give crisp edges, only integers can be used
	 * @return this
	 */
	public ItemCreator setItemScale(int scale) {
		this.itemScale = scale;
		return this;
	}
}
