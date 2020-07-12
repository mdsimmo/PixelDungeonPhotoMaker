package com.mdsimmo.imageEditors

import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.util.stream.Stream

class Crop(private val selection: Rectangle) : Editor {

    override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
        return input.map { asset: BufferedImage? ->
            // gets the asset's image
            val itemImage = BufferedImage(selection.width,
                    selection.height, BufferedImage.TYPE_INT_ARGB)
            itemImage.graphics.drawImage(asset, -selection.x, -selection.y, null)
            itemImage
        }
    }

}