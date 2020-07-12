package com.mdsimmo

import com.mdsimmo.imageEditors.Editor
import java.awt.image.BufferedImage
import java.util.stream.Stream

class PipeLine (private vararg val pipeline: Editor) {

    fun process(input: BufferedImage): Stream<BufferedImage> {
        return pipeline.fold(Stream.of(input)) {
            frames, op -> op.process(frames)
        }
    }

}