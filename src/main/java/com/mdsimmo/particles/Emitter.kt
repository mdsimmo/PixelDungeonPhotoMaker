package com.mdsimmo.particles

class Emitter(
        logic: ParticleLogic,
        framesPerSpawn: Int,
        private val loopTime: Int) {

    private var particleStates = mutableMapOf<Int, Set<ParticleState>>()

    init {
        for (spawnTick in 0 until loopTime step framesPerSpawn) {
            val particleSpawn = logic.newParticle()
            var particle = particleSpawn
            for (simTick in 0 until loopTime) {
                particle = logic.update(particle)
                particleStates.merge((simTick + spawnTick) % loopTime, setOf(particle)) { a, b -> a.union(b) }
            }

            /*if (spawnTick % framesPerSpawn == 0) {
                val particle = ParticleState(logic, particleImg, spawnTick)
                particles.add(particle)
                particle.logic.setLocation(
                        MathHelper.randomRange(-spawnRadius, spawnRadius),
                        MathHelper.randomRange(-spawnRadius, spawnRadius))
            }  */
        }
    }

    fun getParticles(frame: Int): Set<ParticleState> {
        return particleStates[frame % loopTime]!!
    }
}