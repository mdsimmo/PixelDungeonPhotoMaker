package au.net.genesis.mds.particles;

import java.awt.Graphics;
import java.util.ArrayList;

import au.net.genesis.mds.helpers.MathHelper;
import au.net.genesis.mds.particles.Particle.ParticleSystem;
import au.net.genesis.mds.particles.Particle.ParticleType;

public class Emitter {

	private ParticleSystem system;
	private ParticleType type;
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private int particleFlow;
	private int areaRange;

	public Emitter(ParticleSystem system, ParticleType type, int particleFlow,
			int areaRange) {
		this.system = system;
		this.type = type;
		this.particleFlow = particleFlow;
		this.areaRange = areaRange;

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

}
