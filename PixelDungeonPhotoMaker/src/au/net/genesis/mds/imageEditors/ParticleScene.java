package au.net.genesis.mds.imageEditors;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import au.net.genesis.mds.assets.AssetFinder;
import au.net.genesis.mds.assets.WellType;
import au.net.genesis.mds.helpers.GifSequenceWriter;
import au.net.genesis.mds.helpers.GraphicHelper;
import au.net.genesis.mds.helpers.Untils;
import au.net.genesis.mds.particles.Emitter;

public class ParticleScene {

	private WellType wellType;
	private Emitter emitter;
	private int width, height;
	private File outputFile;

	/**
	 * This class can create a particle scene.  
	 */
	public ParticleScene() {
		emitter = new Emitter();
		// default value
		setSceneSize(96, 96).setWellType(WellType.MAGIC_WELL)
			.setOutputFile(AssetFinder.getTempFile(""));
	}

	/**
	 * creates the particle scene. <br>
	 * The function will save all the individual frames and the compiled gif
	 * @return the location of the compiled gif
	 */
	public File createScene(){
		makeFrames();
		return makeGif();
		
	}

	private void makeFrames() {
		int frames = 100;
		float scale;
		emitter.flush();
		
		// generate where the particles should spawn
		for (int loopNumber = 0; loopNumber < frames; loopNumber++) {
			emitter.update(loopNumber, false);
		}
		
		// loop again but this time drawing the frames and not generating particles
		for (int loopNumber = 0; loopNumber < frames; loopNumber++) {
			BufferedImage wellImage = wellType.getImage();
			scale = (float) width / (float) wellImage.getWidth();
			wellImage = GraphicHelper.scaleImage(wellImage, scale, scale);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.getGraphics();
			g.drawImage(wellImage, 0, height - wellImage.getHeight(), null);
			emitter.update(loopNumber, true);
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
				ImageIO.write(image, "png", new File(outputFile.getPath() + "/particle" + Untils.fromatInt(loopNumber, 5)+ ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private File makeGif() {
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String filename) {
				if (filename.matches("particle\\d+\\.png")) {
					return true;
				}
				return false;
			}
		};
		File out = new File(outputFile.getPath()+ "/animation.gif");
		GifSequenceWriter.createGif(outputFile, filter,out, 40, true, true);
		return out;
	}
	
	public ParticleScene setWellType(WellType wellType) {
		this.wellType = wellType;
		return this;
	}

	public Emitter getEmitter() {
		return emitter;
	}

	public ParticleScene setSceneSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * sets the output file. <br>
	 * The appropriate extension will automatically be added to the end
	 * @param file the output directory
	 * @return this
	 */
	public ParticleScene setOutputFile(File file) {
		this.outputFile = file;
		return this;
	}
}
