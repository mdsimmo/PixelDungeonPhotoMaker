package com.mdsimmo.particles

abstract class ParticleLogic {

    abstract fun newParticle(): ParticleState

    open fun update(particle: ParticleState): ParticleState {
        return particle.copy(
                x = particle.x + particle.xspeed,
                y = particle.y + particle.yspeed,
                age = particle.age + 1,
                alpha = if (particle.age >= particle.lifeTime) { 0f } else { particle.alpha }
        )
    }
}