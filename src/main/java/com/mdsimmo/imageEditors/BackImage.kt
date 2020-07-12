package com.mdsimmo.imageEditors

import java.awt.image.BufferedImage
import java.util.stream.Stream

class BackImage(private val image: BufferedImage) : Editor {

    override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
        return input.map { asset: BufferedImage ->

            val tempItem = BufferedImage(image.width, image.height, image.type)
            val graphics = tempItem.graphics
            graphics.drawImage(image, 0, 0, null)
            graphics.drawImage(
                    asset, (image.width - asset.width) / 2, (image.height - asset.height) / 2,
                    asset.width, asset.height, null)

            tempItem
        }
    }

}