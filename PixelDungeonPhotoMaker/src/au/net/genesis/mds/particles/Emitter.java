package au.net.genesis.mds.particles;

import java.awt.Graphics;
import java.util.ArrayList;

import au.net.genesis.mds.assets.ParticleType;
import au.net.genesis.mds.helpers.MathHelper;
import au.net.genesis.mds.particles.Particle.ParticleSystem;

public class Emitter {

	private ParticleSystem system;
	private ParticleType type;
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private int particleFlow;
	private int areaRange;

	public Emitter() {
		// set default values
		setParticleSystem(ParticleSystem.BUBBLE)
			.setParticleType(ParticleType.BUBBLE)
			.setParticleFlow(10)
			.setAreaRange(4);
	}

	public void update(int tick, boolean looped) {
		for (Particle particle : particles) {
			particle.update();
		}
		if (!looped) {
			if (tick % particleFlow == 0) {
				Particle particle = new Particle(this.system, this.type, tick);
				particles.add(particle);
				particle.getLogic().setLocation(
						MathHelper.randomRange(-areaRange, areaRange),
						MathHelper.randomRange(-areaRange, areaRange));
			}
		} else {
			for (Particle particle : particles) {
				if (particle.getLogic().getSpawnTime() == tick) {
					particle.getLogic().initialise();
				}
			}
		}
	}

	public void draw(Graphics g, int x, int y, float scale) {
		for (Particle particle : particles) {
			particle.draw(g, x, y, scale);
		}
	}
	
	public Emitter setParticleSystem(ParticleSystem system) {
		this.system = system;
		return this;
	}
	

	public Emitter setParticleType(ParticleType type) {
		this.type = type;
		return this;
	}
	
	public Emitter setParticleFlow(int flow) {
		this.particleFlow = flow;
		return this;
	}
	
	public Emitter setAreaRange(int range) {
		this.areaRange = range;
		return this;
	}
	
}
