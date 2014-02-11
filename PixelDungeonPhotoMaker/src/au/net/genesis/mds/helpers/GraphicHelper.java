package au.net.genesis.mds.helpers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class GraphicHelper {

	public static BufferedImage tranformImage(BufferedImage image,
			double xscale, double yscale, double rotation, double alpha) {
		// apply translations on buffered image
		image = scaleImage(image, xscale, yscale);
		image = rotateImage(image, rotation);
		image = applyAlpha(image, alpha);

		return image;

	}

	public static BufferedImage rotateImage(BufferedImage image, double rotation) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);

		double rotationRequired = Math.toRadians(rotation);
		double locationX = width / 2;
		double locationY = height / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(
				rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);

		BufferedImage newImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) newImage.getGraphics();
		// Drawing the rotated image at the required drawing locations
		g2d.drawImage(op.filter(image, null), 0, 0, null);

		return newImage;
	}

	public static BufferedImage applyAlpha(BufferedImage image, double alpha) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage newImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				(float) alpha);
		Graphics2D g2d = (Graphics2D) newImage.getGraphics();
		g2d.setComposite(ac);
		g2d.drawImage(image, 0, 0, null);

		return newImage;
	}

	public static BufferedImage scaleImage(BufferedImage image, double xscale,
			double yscale) {
		int width = (int) ((double) (image.getWidth(null)) * xscale);
		int height = (int) ((double) (image.getHeight(null)) * yscale);
		Image newImage = image.getScaledInstance(width, height,
				Image.SCALE_FAST);
		BufferedImage newBuffered = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		newBuffered.getGraphics().drawImage(newImage, 0, 0, null);
		return newBuffered;
	}

	
	
	
	public static BufferedImage dropShadow(BufferedImage image, int shadowSize, double opacity, Color color) {
		image = createShadowMask(image, shadowSize, opacity, color);
		
		return image;
	}

	private static BufferedImage createShadowMask(BufferedImage image, int shadowSize, double opacity, Color color) {
		BufferedImage mask = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int argb = image.getRGB(x, y);
				argb = (int) ((argb >> 24 & 0xFF) * opacity) << 24
						| color.getRGB() & 0x00FFFFFF;
				mask.setRGB(x, y, argb);
			}
		}
		return mask;
	}

}
