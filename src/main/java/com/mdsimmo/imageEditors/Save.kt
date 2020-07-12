package com.mdsimmo.imageEditors

import com.mdsimmo.helpers.GifSequenceWriter
import java.awt.image.BufferedImage
import java.io.File
import java.util.stream.Stream
import javax.imageio.ImageIO
import javax.imageio.stream.FileImageOutputStream

class Save(private val file: File,
           private val msBtwFrames: Int = 0,
           private val looped: Boolean = true) : Editor {

    override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
        if (file.extension.equals("gif", ignoreCase = true)) {
            writeGif(input)
        } else {
            var index = 0
            input.forEach {
                writePng(it, index++)
            }
        }

        return Stream.empty()
    }

    private fun writeGif(input: Stream<BufferedImage>) {
        GifSequenceWriter(FileImageOutputStream(file), BufferedImage.TYPE_INT_ARGB, msBtwFrames, looped, true)
                .use { writer ->
                    input.forEachOrdered { frame ->
                        writer.writeToSequence(frame)
                    }
                }
    }

    private fun writePng(input: BufferedImage, index: Int) {
        // TODO add index extension
        ImageIO.write(input, file.extension, File(file.parentFile, file.nameWithoutExtension + "_" + index  + "." + file.extension))
    }
}