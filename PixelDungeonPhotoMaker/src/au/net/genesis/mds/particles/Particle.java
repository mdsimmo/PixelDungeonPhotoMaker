package au.net.genesis.mds.particles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import au.net.genesis.mds.helpers.GraphicHelper;

public class Particle {

	public enum ParticleSystem {

		FLOAT_UP(FloatUp.class), POP_UP(PopUp.class), FLY_AWAY(FlyAway.class), BUBBLE(
				Bubble.class);

		private Class<? extends ParticleLogic> clazz;

		ParticleSystem(Class<? extends ParticleLogic> clazz) {
			this.clazz = clazz;
		}

		Class<? extends ParticleLogic> getClazz() {
			return clazz;
		}

	}

	public enum ParticleType {

		PLUS("plus.png"), MARK("mark.png"), BUBBLE("bubble.png"), BUTTERFLY(
				"butterfly.png");

		private String file;

		private ParticleType(String file) {
			this.file = file;
		}

		String getFile() {
			return "resources/" + file;
		}

	}

	private ParticleLogic logic;
	private BufferedImage texture;

	public Particle(ParticleSystem system, ParticleType type, int time) {
		try {
			texture = ImageIO.read(new File(type.getFile()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// give some room around the edge so it can be rotated
		BufferedImage replacement = new BufferedImage(texture.getWidth() * 2,
				texture.getHeight() * 2, BufferedImage.TYPE_INT_ARGB);
		replacement.getGraphics().drawImage(texture, texture.getWidth() / 2,
				texture.getHeight() / 2, null);
		texture = replacement;

		try {
			logic = system.getClazz().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		logic.initialise();
		logic.setSpawnTime(time);

	}

	public void update() {
		logic.update();
	}

	public void draw(Graphics g, int x, int y, float scale) {
		BufferedImage speckImage = GraphicHelper.tranformImage(texture, logic.xscale
				* scale, logic.yscale * scale, logic.rotation, logic.alpha);
		g.drawImage(speckImage, (int) (logic.getx()*scale + x - speckImage.getWidth() / 2),
				(int) (logic.gety()*scale + y - speckImage.getHeight() / 2), null);
	}

	public ParticleLogic getLogic() {
		return logic;
	}
}
