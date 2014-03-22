package au.net.genesis.mds.particles;


public class FloatUp extends ParticleLogic {

	
	@Override
	public void initialise() {
		super.initialise();
		yspeed = -1;
		alpha = 0;
		xscale = yscale =1F;
	}

	@Override
	public void update() {
		super.update();
		if (age > lifeTime - 10) {
			alpha = Math.max(alpha-.1F, 0F);
		} else {
			alpha = Math.min(alpha+.2F, 1F);
		}
	}
}
