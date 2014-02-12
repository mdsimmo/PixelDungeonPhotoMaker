package au.net.genesis.mds.particles;

import au.net.genesis.mds.helpers.MathHelper;

public class FlyAway extends ParticleLogic {
	
	private final int myRotation = MathHelper.randomRange(0, 360);
	private float speed = 0.5F;
	
	@Override
	public void initialise() {
		super.initialise();
		lifeTime = 25;
		alpha = 0;
		rotation = myRotation+90;
		xspeed = (float) MathHelper.xcomponent(speed, myRotation);
		yspeed = (float) MathHelper.ycomponent(speed, myRotation);
	}
	public void update() {
		super.update();
		xscale = (Math.abs((age % 10)-5)+1)/5F;
		if (age > lifeTime-10) {
			alpha = Math.max(alpha-0.1F, 0F);
		} else {
			alpha = Math.min(1F, alpha + 0.2F);
		}
	}

}
