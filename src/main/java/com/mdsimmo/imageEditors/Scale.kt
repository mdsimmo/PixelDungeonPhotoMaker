package com.mdsimmo.imageEditors

import com.mdsimmo.helpers.GraphicHelper
import java.awt.image.BufferedImage
import java.util.stream.Stream

class Scale(private val xScale: Double, private val yScale: Double) : Editor {

    override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
        return input.map { GraphicHelper.scaleImage(it, xScale, yScale) }
    }

}