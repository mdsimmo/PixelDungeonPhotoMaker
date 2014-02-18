package au.net.genesis.mds.imageEditors;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import au.net.genesis.mds.helpers.GraphicHelper;

public class BasicImageCreator {

	private BufferedImage asset = null;
	private Rectangle selection;
	private int itemScale = 3;

	/**
	 * This class will take an asset file and generate an info box
	 */
	public BasicImageCreator() {
		selection = new Rectangle(0, 0, 16, 16);
	}

	/**
	 * Sets the file to take the asset from.
	 * 
	 * @param fileName
	 *            the name of the asset file
	 * @return this
	 */
	public BasicImageCreator setAsset(String fileName) {
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
	 * Any white space is around the selection is automatically striped away.
	 * 
	 * @param selection
	 *            the rectangle containing only the item's image
	 * @return this
	 */
	public BasicImageCreator setSelection(Rectangle selection) {
		this.selection = selection;
		return this;
		// (white space stripping done in getImage())
	}

	/**
	 * Gets the final infobox image
	 * 
	 * @return the infobox's image
	 */
	public BufferedImage getImage() {
		if (asset != null && selection != null) {
			
			// gets the asset's image
			BufferedImage itemImage = new BufferedImage(selection.width,
					selection.height, BufferedImage.TYPE_INT_ARGB);
			itemImage.getGraphics().drawImage(asset, -selection.x, -selection.y, null);
			/*
			BufferedImage temp;
			// remove whitespace (lets the image can be auto centered)
			int minx = itemImage.getWidth() / 2;
			int maxx = minx;
			int miny = itemImage.getHeight() / 2;
			int maxy = miny;
			for (int i = 0; i < itemImage.getWidth(); i++) {
				for (int j = 0; j < itemImage.getHeight(); j++) {
					boolean isOpaque = ((itemImage.getRGB(i, j) & 0xFF000000) != 0x00000000);
					if (isOpaque) {
						if (i < minx)
							minx = i;
						if (i > maxx)
							maxx = i;
						if (j < miny)
							miny = j;
						if (j > maxy)
							maxy = j;
					}
				}
			}
			BufferedImage temp = new BufferedImage(maxx - minx + 1, maxy - miny
					+ 1, BufferedImage.TYPE_INT_ARGB);
			temp.getGraphics().drawImage(itemImage, -minx, -miny, null);
			itemImage = temp;
			temp.flush();
			
			// sets the minimum size to be 16 x 16
			if (itemImage.getWidth() < 16) {
				 temp = new BufferedImage(16, itemImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
				 temp.getGraphics().drawImage(itemImage, (16-itemImage.getWidth())/2, 0, null);
				 itemImage = temp;
				 temp.flush();
			}
			// (height min 16 too)
			if (itemImage.getHeight() < 16) {
				 temp = new BufferedImage(itemImage.getHeight(), 16, BufferedImage.TYPE_INT_ARGB);
				 temp.getGraphics().drawImage(itemImage, 0, (16-itemImage.getHeight())/2, null);
				 itemImage = temp;
				 temp.flush();
			}
			*/
			
			// scales the items image
			itemImage = GraphicHelper.scaleImage(itemImage, itemScale, itemScale);
			
			return itemImage;
		} else {
			System.err.println("Infobox could not draw: not all has information initialised!");
			return null;
		}
	}

	/**
	 * Set the factor that the item should be scaled up by. <br>
	 * Usually, items are scaled by 12; mobs: 12-16 (there are cases where these
	 * values shouldn't be followed)
	 * 
	 * @param scale
	 *            the scale. To give crisp edges, only integers can be used
	 * @return this
	 */
	public BasicImageCreator setItemScale(int scale) {
		this.itemScale = scale;
		return this;
	}
}
