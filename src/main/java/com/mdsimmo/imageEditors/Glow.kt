package com.mdsimmo.imageEditors

import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.util.stream.IntStream
import java.util.stream.Stream
import kotlin.math.sin

class Glow(private val color: Color, private val length: Int) : Editor {

    override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
        return input.flatMap { base: BufferedImage ->
            // TODO what is the point of this setup stuff?
            // Why not just use base directly?
            val glow = BufferedImage(base.width, base.height, base.type)
            val g = glow.graphics as Graphics2D
            g.color = color
            g.fillRect(0, 0, base.width, base.height)
            g.composite = AlphaComposite.getInstance(AlphaComposite.DST_IN)
            g.drawImage(base, 0, 0, null)

            IntStream.range(0, length).mapToObj { i: Int ->
                // copy the base image
                val frame = BufferedImage(base.width, base.height, base.type)
                val g2 = frame.graphics as Graphics2D
                g2.drawImage(base, 0, 0, null)

                // color the image
                val alpha = (sin(i.toFloat() * 2f * Math.PI / length).toFloat() + 2f) / 5f
                val ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)
                g2.composite = ac
                g2.drawImage(glow, 0, 0, null)

                frame
            }
        }
    }
}