package com.mdsimmo.imageEditors

import java.awt.Color
import java.awt.image.BufferedImage
import java.util.stream.Stream

class BackFill(private val color: Color) : Editor {

    override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
        return input.map { asset: BufferedImage ->

            val tempItem = BufferedImage(asset.width, asset.height, asset.type)
            val graphics = tempItem.graphics
            graphics.color = color
            graphics.fillRect(0, 0, asset.width, asset.height)
            graphics.drawImage(asset, 0, 0, null)

            tempItem
        }
    }

}