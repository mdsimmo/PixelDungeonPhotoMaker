package com.mdsimmo.imageEditors

import com.mdsimmo.helpers.GraphicHelper
import com.mdsimmo.particles.Emitter
import com.mdsimmo.particles.ParticleState
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.util.stream.IntStream
import java.util.stream.Stream

class Particles(val emitter: Emitter, val texture: BufferedImage, val scale: Float, val frames: Int) : Editor {

    override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
        return input.flatMap { background ->
            IntStream.range(0, frames).mapToObj { tick ->
                val frame = BufferedImage(background.width, background.height, BufferedImage.TYPE_INT_ARGB)
                val g = frame.graphics
                g.drawImage(background, 0, 0, null)
                val particles = emitter.getParticles(tick)
                particles.forEach {
                    draw(g, it, frame.width / 2, frame.height / 2)
                }
                frame
            }
        }
    }

    private fun draw(g: Graphics, particle: ParticleState, x: Int, y: Int) {
        val speckImage = GraphicHelper.tranformImage(texture,
                (particle.xscale * scale).toDouble(), particle.yscale * scale.toDouble(),
                particle.rotation.toDouble(), particle.alpha.toDouble())
        g.drawImage(speckImage, (particle.x * scale + x - speckImage.width / 2).toInt(),
                (particle.y * scale + y - speckImage.height / 2).toInt(), null)
    }
}