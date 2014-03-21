package au.net.genesis.mds.imageEditors;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.assets.InfoboxBack;
import au.net.genesis.mds.helpers.GraphicHelper;

import com.jhlabs.image.ShadowFilter;

public class InfoboxCreator {

	private BufferedImage asset = null;
	private Rectangle selection;
	private InfoboxBack background = InfoboxBack.SEWER;
	private int itemScale = 14;
	private int shadowRadius = 12;
	private float shadowOpacity = 0.8F;
	private File outputFile = AssetFinder.getTempFile("save.png");

	/**
	 * This class will take an asset file and generate an info box
	 */
	public InfoboxCreator() {
		selection = new Rectangle(0, 0, 16, 16);
	}

	/**
	 * Sets the file to take the asset from.
	 * 
	 * @param fileName
	 *            the name of the asset file
	 * @return this
	 */
	public InfoboxCreator setAsset(File fileName) {
		asset = AssetFinder.loadImage(fileName);
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
	public InfoboxCreator setSelection(Rectangle selection) {
		this.selection = selection;
		return this;
		// (white space stripping done in getImage())
	}

	/**
	 * Sets the infobox's background
	 * 
	 * @param background
	 *            the background to use
	 * @return this
	 */
	public InfoboxCreator setBackground(InfoboxBack background) {
		this.background = background;
		return this;
	}
	
	public InfoboxCreator setOutputFile(File out) {
		outputFile = out;
		return this;
	}

	/**
	 * Gets the final infobox image
	 * 
	 * @return the infobox's image
	 */
	public File getImage() {
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
			itemImage.getGraphics().drawImage(asset, -selection.x,
					-selection.y, null);

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

			// modifies the items image
			itemImage = GraphicHelper.scaleImage(itemImage, itemScale,
					itemScale);
			itemImage = dropShadow(itemImage);

			// draws the item's image onto the background
			g.drawImage(itemImage,
					(InfoboxBack.BACKGROUND_SIZE - itemImage.getWidth()) / 2,
					(InfoboxBack.BACKGROUND_SIZE - itemImage.getHeight()) / 2,
					null);
			
			// saves and returns the final image
			try {
				ImageIO.write(image, "png", outputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return outputFile;
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
	public InfoboxCreator setItemScale(int scale) {
		this.itemScale = scale;
		return this;
	}
	
	public int getItemScale() {
		return itemScale;
	}

	/**
	 * Places a drop shadow on the image
	 * 
	 * @param image
	 *            the image to have a drop shadow
	 * @return the drop shadowed image
	 */
	private BufferedImage dropShadow(BufferedImage image) {
		// Thanks to www.jhlabs.com for the shadowing tools
		ShadowFilter sf = new ShadowFilter(shadowRadius, 0, 0, shadowOpacity);
		// create extra room around the image
		BufferedImage newImage = new BufferedImage(image.getWidth()
				+ shadowRadius * 2, image.getHeight() + shadowRadius * 2,
				BufferedImage.TYPE_INT_ARGB);
		newImage.getGraphics().drawImage(image, shadowRadius, shadowRadius,
				null);
		// I don't actually know how to use this filter :P
		newImage = sf.filter(newImage, null);
		return newImage;
	}

	/**
	 * Configures the drop shadow's properties
	 * 
	 * @param radius
	 *            the radius of the drop shadow
	 * @param opacity
	 *            the opacity of the drop shadow (range 0 - 1)
	 * @return this
	 */
	public InfoboxCreator configureShadow(int radius, float opacity) {
		this.shadowRadius = radius;
		this.shadowOpacity = opacity;
		return this;
	}
	public int getShadowRadius() {
		return shadowRadius;
	}
	public float getShadowOpacity() {
		return shadowOpacity;
	}
}
