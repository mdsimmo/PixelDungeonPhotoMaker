package com.mdsimmo.imageEditors

import java.awt.image.BufferedImage
import java.util.stream.Stream

class SpriteSheet(val width: Int, val height: Int,
                  val xOffset: Int = 0, val yOffset: Int = 0,
                  val xSpace: Int = 0, val ySpace: Int = 0,
                  val indices: List<Int>) : Editor {

    override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
        return input.flatMap { asset: BufferedImage ->

            val framesPerRow = (asset.width - xOffset) / (xSpace + width)

            indices.stream().map { index ->
                val frame = BufferedImage(width, height, asset.type)
                val graphics = frame.graphics
                val xIndex = index % framesPerRow
                val yIndex = index / framesPerRow
                graphics.drawImage(asset,
                        -(xOffset + xIndex * (width + xSpace)), -(yOffset + yIndex * (height + ySpace)),
                        null)

                frame
            }
        }
    }

}