package com.mdsimmo.imageEditors

import java.awt.image.BufferedImage
import java.util.stream.Stream

class Trim : Editor {

    override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
        return input.map { asset: BufferedImage ->

            // remove whitespace (lets the image can be auto centered)
			var minx = asset.width / 2
			var maxx = minx
			var miny = asset.height / 2
			var maxy = miny
			for (i in 0 until asset.width) {
				for (j in 0 until asset.height) {
					val isOpaque = ((asset.getRGB(i, j).toLong() and 0xFF000000) != 0x00000000L)
					if (isOpaque) {
						if (i < minx)
							minx = i
						if (i > maxx)
							maxx = i
						if (j < miny)
							miny = j
						if (j > maxy)
							maxy = j
					}
				}
			}
			val trimmed = BufferedImage(maxx - minx + 1, maxy - miny + 1, BufferedImage.TYPE_INT_ARGB)
			trimmed.graphics.drawImage(asset, -minx, -miny, null)

            trimmed
        }
    }

}