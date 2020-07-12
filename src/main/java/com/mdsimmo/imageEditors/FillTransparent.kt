package com.mdsimmo.imageEditors

import java.awt.Color
import java.awt.image.BufferedImage
import java.util.stream.Stream

class FillTransparent(private val color: Color) : Editor {

    override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
        return input.map { asset: BufferedImage ->

            // TODO use graphic masks to do this faster
            val background = BufferedImage(asset.width, asset.height, BufferedImage.TYPE_INT_ARGB)
            val bg = background.graphics
            bg.color = color
            for (i in 0 until asset.width) {
                for (j in 0 until asset.height) {
                    val alpha = asset.getRGB(i, j) shr 24 and 0xFF
                    if (alpha != 0) {
                        bg.fillRect(i, j, 1, 1)
                    }
                }
            }
            bg.drawImage(asset, 0, 0, null)

            background
        }
    }

}