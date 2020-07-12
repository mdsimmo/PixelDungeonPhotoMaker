package com.mdsimmo.particles

import com.mdsimmo.helpers.MathHelper
import kotlin.math.abs

class FlyAway : ParticleLogic() {

    override fun newParticle(): ParticleState {
        val rotation = MathHelper.randomRange(0, 360)
        val speed = 0.5f
        return ParticleState(
            lifeTime = 25,
            alpha = 0f,
            rotation = rotation + 90,
            xspeed = MathHelper.xcomponent(speed.toDouble(), rotation.toDouble()).toFloat(),
            yspeed = MathHelper.ycomponent(speed.toDouble(), rotation.toDouble()).toFloat()
        )
    }

    override fun update(particle: ParticleState): ParticleState {
        return super.update(particle).copy(
            xscale = (abs(particle.age % 10 - 5) + 1) / 5f,
            alpha = if (particle.age > particle.lifeTime - 10) {
                        (particle.alpha - 0.1f).coerceAtLeast(0f)
                    } else {
                        (particle.alpha + 0.2f).coerceAtMost(1f)
                    }
        )
    }
}