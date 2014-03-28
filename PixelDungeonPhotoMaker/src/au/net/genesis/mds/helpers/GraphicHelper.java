package au.net.genesis.mds.helpers;

import java.awt.AlphaComposite;
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
		int width = image.getWidth();
		int height = image.getHeight();

		double rotationRequired = Math.toRadians(rotation);
		double locationX = width / 2;
		double locationY = height / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(
				rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);

		BufferedImage newImage = new BufferedImage(width, height, image.getType());
		Graphics2D g2d = (Graphics2D) newImage.getGraphics();
		// Drawing the rotated image at the required drawing locations
		g2d.drawImage(op.filter(image, null), 0, 0, null);

		return newImage;
	}

	public static BufferedImage applyAlpha(BufferedImage image, double alpha) {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage newImage = new BufferedImage(width, height, image.getType());
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				(float) alpha);
		Graphics2D g2d = (Graphics2D) newImage.getGraphics();
		g2d.setComposite(ac);
		g2d.drawImage(image, 0, 0, null);

		return newImage;
	}

	public static BufferedImage scaleImage(BufferedImage image, double xscale,
			double yscale) {
		int width = (int) ((double) (image.getWidth()) * xscale);
		int height = (int) ((double) (image.getHeight()) * yscale);
		width = Math.max(1, width);
		height = Math.max(1, height);
		Image newImage = image.getScaledInstance(width, height,
				Image.SCALE_FAST);
		BufferedImage newBuffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		newBuffered.getGraphics().drawImage(newImage, 0, 0, null);
		return newBuffered;
	}
}
