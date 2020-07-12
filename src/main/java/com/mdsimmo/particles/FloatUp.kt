package com.mdsimmo.particles

class FloatUp : ParticleLogic() {
    override fun newParticle(): ParticleState {
        return ParticleState(
            yspeed = -1f,
            alpha = 0f
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