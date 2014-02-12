package au.net.genesis.mds.imageEditors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import au.net.genesis.mds.assets.WellType;
import au.net.genesis.mds.helpers.GraphicHelper;
import au.net.genesis.mds.particles.Emitter;
import au.net.genesis.mds.particles.Particle.ParticleSystem;
import au.net.genesis.mds.particles.Particle.ParticleType;

public class ParticleScene extends Thread {

	private WellType well;
	private Emitter emitter;
	private boolean process = false;
	private int width, height;

	/**
	 * Creates a scene with a well and an emitter
	 * 
	 * @param wellType
	 *            The type of well
	 * @param system
	 *            the system for the particles
	 * @param type
	 *            what the particles look like
	 * @param particleFlow
	 *            inversely proportional to the amount of particles spawned per
	 *            second
	 * @param areaRange
	 *            the spawn radius
	 * @param width
	 *            the width of the final image (image will scale to match)
	 * @param height
	 *            the height of the final image (will place whitespace above
	 *            image if neccercary)
	 */
	public ParticleScene(WellType wellType, ParticleSystem system, ParticleType type,
			int particleFlow, int areaRange, int width, int height) {
		well = wellType;
		emitter = new Emitter(system, type, particleFlow, areaRange);
		this.width = width;
		this.height = height;
	}

	/**
	 * Call this method to bake a particle scene 
	 */
	public void begin() {
		this.process = true;
		this.start();
	}

	public void finish() {
		this.process = false;
	}

	
	public void run() {

		int loopNumber = 0;
		boolean looped = false;
		int frames = 100;
		float scale;
		while (process) {
			BufferedImage wellImage = well.getImage();
			scale = (float) width / (float) wellImage.getWidth();
			wellImage = GraphicHelper.scaleImage(wellImage, scale, scale);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.getGraphics();
			g.drawImage(wellImage, 0, height - wellImage.getHeight(), null);
			emitter.update(loopNumber, looped);
			emitter.draw(g, width / 2, height - wellImage.getHeight() / 2,
					scale);

			// make sure there's no semi transparent pixels
			BufferedImage background = new BufferedImage(image.getWidth(),
					image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics bg = background.getGraphics();
			bg.setColor(new Color(71, 70, 70));
			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
					int alpha = (image.getRGB(i, j) >> 24) & 0xFF;
					if (alpha != 0) {
						bg.fillRect(i, j, 1, 1);
					}
				}
			}
			bg.drawImage(image, 0, 0, null);
			image = background;

			try {
				ImageIO.write(image, "png", new File("output/saved"
						+ loopNumber + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			loopNumber += 1;
			if (loopNumber >= frames) {
				if (looped) {
					this.finish();
				} else {
					looped = true;
					loopNumber = 0;
				}
			}
		}
	}

}
