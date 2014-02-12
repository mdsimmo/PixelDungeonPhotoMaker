package au.net.genesis.mds.particles;

public class PopUp extends ParticleLogic{

	
	@Override
	public void initialise() {
		super.initialise();
		alpha = 0;
		xscale = yscale = 0.4F;
		lifeTime = 30;
	}
	
	@Override
	public void update() {
		super.update();
		if (age < lifeTime/2) {
			alpha = Math.min(1F , alpha + 0.1F);
			yscale = xscale += 0.15;
		} else {
			alpha = Math.max(0F , alpha - 0.1F);
			yscale = xscale = Math.max(xscale - 0.15F, 1F);
		}
	}
	
}
