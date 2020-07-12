package com.mdsimmo.particles

import com.mdsimmo.helpers.MathHelper

class Bubble : ParticleLogic() {

    override fun newParticle(): ParticleState {
        val scale = MathHelper.randomRange(1f, 1.6f)
        return ParticleState(
            alpha = 0f,
            yspeed = -0.5f,
            yscale = scale,
            xscale = scale
        )
    }

    override fun update(particle: ParticleState): ParticleState {
        return super.update(particle).copy(
                alpha = if (particle.age > particle.lifeTime - 10) {
                    (particle.alpha - .1f).coerceAtLeast(0f)
                } else {
                    (particle.alpha + .2f).coerceAtMost(1f)
                }
        )
    }
}