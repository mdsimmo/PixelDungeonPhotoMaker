package com.mdsimmo.imageEditors

import com.jhlabs.image.ShadowFilter
import java.awt.image.BufferedImage
import java.util.stream.Stream


class Shadow(val radius: Int, val opacity: Float, val xOffset: Int, val yOffset: Int) : Editor {

    override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
        return input.map { asset: BufferedImage ->
            // Thanks to www.jhlabs.com for the shadowing tools
            val sf = ShadowFilter(radius, xOffset, yOffset, opacity)
            // create extra room around the image
            // create extra room around the image
            val newImage = BufferedImage(
                    asset.width + radius * 2, asset.height + radius * 2,
                    BufferedImage.TYPE_INT_ARGB
            )
            newImage.graphics.drawImage(asset, radius, radius, null)

            sf.filter(newImage, null)
        }
    }

}