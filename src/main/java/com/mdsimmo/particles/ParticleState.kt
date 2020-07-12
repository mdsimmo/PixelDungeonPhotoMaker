package com.mdsimmo.particles

import com.mdsimmo.helpers.MathHelper

data class ParticleState (
        val x: Float = MathHelper.randomRange(-4, 4).toFloat(),
        val y: Float = MathHelper.randomRange(-4, 4).toFloat(),
        val lifeTime: Int = 20,
        val age: Int = 0,
        val rotation: Int = 0,
        val xspeed: Float = 0f,
        val yspeed: Float = 0f,
        val alpha: Float = 1f,
        val xscale: Float = 1f,
        val yscale: Float = 1f
)