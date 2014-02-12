package au.net.genesis.mds.particles;


public class ParticleLogic {

	protected float x;
	protected float y;
	protected int lifeTime = 20;
	protected int age;
	protected int rotation;
	protected float xspeed, yspeed;
	protected float alpha, xscale, yscale;
	protected int startx = 0, starty = 0;
	protected int spawnTime;


	public void update() {
		age += 1;
		if (age >= lifeTime) {
			// TODO destroy me
		}
		x += xspeed;
		y += yspeed;
	}
	

	public int getx() {
		return (int)x;
	}

	public int gety() {
		return (int)y;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.startx = x;
		this.starty = y;
	}
	public void setLifeTime(int lifeTime) {
		this.lifeTime += lifeTime;
	}
	
	public void setSpawnTime(int time) {
		this.spawnTime = time;
	}
	
	public void initialise() {
		age = 0;
		alpha = 1;
		xscale = yscale = 1;
		rotation = 0;
		xspeed = 0;
		yspeed = 0;
		x = startx;
		y = starty;
	}
	public int getSpawnTime() {
		return spawnTime;
	}
}
