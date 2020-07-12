package com.mdsimmo.particles

class PopUp : ParticleLogic() {

    override fun newParticle(): ParticleState {
        return ParticleState(
            alpha = 0f,
            yscale = 0.4f,
            xscale = 0.4f,
            lifeTime = 30
        )
    }

    override fun update(particle: ParticleState): ParticleState {
        val p = super.update(particle)
        return if (p.age < p.lifeTime / 2) {
            p.copy(
                    alpha = Math.min(1f, p.alpha + 0.1f),
                    xscale = p.xscale + 0.15f,
                    yscale = p.yscale + 0.15f
            )
        } else {
            p.copy(
                    alpha = Math.max(0f, p.alpha - 0.1f),
                    xscale = p.xscale - 0.15f,
                    yscale = p.yscale - 0.15f
            )
        }
    }
}