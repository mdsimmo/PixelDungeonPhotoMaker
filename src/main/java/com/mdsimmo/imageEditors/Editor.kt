package com.mdsimmo.imageEditors

import java.awt.image.BufferedImage
import java.util.stream.Stream

interface Editor {

    fun process(input: Stream<BufferedImage>): Stream<BufferedImage>

}