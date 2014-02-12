package au.net.genesis.mds.particles;

import au.net.genesis.mds.helpers.MathHelper;

public class Bubble extends ParticleLogic {
	
	private float scale = MathHelper.randomRange(1F,1.6F);
	
	@Override
	public void update() {
		super.update();
		if (age > lifeTime - 10) {
			alpha = Math.max(alpha-.1F, 0F);
		} else {
			alpha = Math.min(alpha+.2F, 1F);
		}
	}
	
	@Override
	public void initialise() {
		super.initialise();
		alpha = 0;
		yspeed = -0.5F;
		xscale = yscale = scale;
	}
}
