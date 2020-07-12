package com.mdsimmo.cmd

import com.mdsimmo.imageEditors.*
import com.mdsimmo.particles.*
import java.awt.Color
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import java.util.stream.Stream
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val cmd = args.joinToString(separator = " ") { it }
    process(cmd.trim())
}

private fun process(cmd: String): Stream<BufferedImage> {
    if (!cmd.startsWith("{"))
        process("{$cmd")
    if (!cmd.endsWith("}"))
        process("$cmd}")

    return expandBrace(cmd)
}

private fun expandBrace(cmdWithBraces: String): Stream<BufferedImage> {
    // strip the start and end braces
    if (!cmdWithBraces.startsWith("{"))
        throw IllegalArgumentException("must start with a brace")
    if (!cmdWithBraces.endsWith("}"))
        throw IllegalArgumentException("must end with a brace")
    val cmd = cmdWithBraces.substring(1, cmdWithBraces.length - 2)

    val iterator = cmd.iterator()

    val partCmds = mutableListOf<String>()
    do {
        val (part, last) = jumpNext(iterator, '|', true)
        partCmds += part
    } while (!last)

    val parts = partCmds
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { expandPart(it) }
            .map { createEditor(it) }

    return parts.fold(Stream.empty<BufferedImage>()) { input, editor -> editor.process(input) }
}

private fun jumpNext(cmd: CharIterator, ch: Char, keepMarkup: Boolean): Pair<String, Boolean> {
    val builder = StringBuilder()
    var braceDepth = 0
    var inQuote = false
    var escapeNext = false
    for (c2 in cmd) {
        if (escapeNext) {
            builder.append(c2)
            escapeNext = false
        } else {
            if (inQuote) {
                if (c2 == '\'') {
                    inQuote = false
                    if (braceDepth > 0 || keepMarkup)
                        builder.append(c2)
                } else {
                    builder.append(c2)
                }
            } else {
                when (c2) {
                    '{' -> {
                        ++braceDepth
                        builder.append(c2)
                    }
                    '}' -> {
                        --braceDepth
                        builder.append(c2)
                    }
                    '\'' -> {
                        inQuote = true
                        if (braceDepth > 0 || keepMarkup)
                            builder.append(c2)
                    }
                    '\\' -> {
                        escapeNext = true
                        if (keepMarkup)
                            builder.append(c2)
                    }
                    else -> {
                        if (c2 == ch && braceDepth == 0) {
                            return Pair(builder.toString(), false)
                        } else if (braceDepth == -1) {
                            // should never hit a end brace without first finding a start brace
                            // since start and end brace is already stripped away
                            throw IllegalArgumentException("Braces not correctly matched")
                        } else {
                            builder.append(c2)
                        }
                    }
                }
            }
        }
    }
    return Pair(builder.toString(), true)
}

data class Part(
        val cmd: String,
        val args: List<String>,
        val params: Map<String, String>
)

private fun expandPart(part: String): Part {
    val iterator = part.iterator()
    val strList = mutableListOf<String>()
    do {
        val (str, last) = jumpNext(iterator, ' ', true)
        val trimmed = str.trim()
        if (trimmed.isNotEmpty()) {
            strList += trimmed
        }
    } while (!last)

    val cmd = strList.first()
    val args = mutableListOf<String>()
    val params = mutableMapOf<String, String>()

    strList.stream().skip(1).forEachOrdered { str ->
        val iter = str.iterator()
        val (prefix, last) = jumpNext(iter, ':', false)
        if (last) {
            args += prefix
        } else {
            val (theRest, mustBeLast) = jumpNext(iter, ' ', false)
            if (!mustBeLast) {
                throw RuntimeException("Should never happen")
            }
            params[prefix] = theRest
        }
    }

    return Part(cmd, args, params)
}

// TODO converters give very bad error messages
private val converters = mapOf<String, (Part) -> Editor>(
        Pair("backfill") { part ->
            BackFill(Color(
                    part.params.getValue("r").toInt(),
                    part.params.getValue("g").toInt(),
                    part.params.getValue("b").toInt()
            ))},
        Pair("backimage") { part ->
            BackImage(expandBrace(part.args.first()).findFirst().get())
        },
        Pair("crop") { part ->
            Crop(Rectangle(
                    part.params.getValue("x").toInt(),
                    part.params.getValue("y").toInt(),
                    part.params.getValue("w").toInt(),
                    part.params.getValue("h").toInt()
            ))
        },
        Pair("filltransparent") { part ->
            FillTransparent(Color(
                    part.params.getValue("r").toInt(),
                    part.params.getValue("g").toInt(),
                    part.params.getValue("b").toInt()
            ))
        },
        Pair("glow") { part ->
            Glow(Color(
                    part.params.getValue("r").toInt(),
                    part.params.getValue("g").toInt(),
                    part.params.getValue("b").toInt()
            ), length = part.params.getValue("length").toInt())
        },
        Pair("load") { part: Part ->
            object : Editor {
                override fun process(input: Stream<BufferedImage>): Stream<BufferedImage> {
                    val file = File(part.args[0])
                    if (!file.exists())
                        throw IllegalArgumentException("Cannot find " + file.absoluteFile)
                    return Stream.of(ImageIO.read(file))
                }
            }
        },
        Pair("particles") { part ->
            val logic = when (part.params.getValue("type")) {
                "bubble" -> Bubble()
                "floatup" -> FloatUp()
                "flyaway" -> FlyAway()
                "popup" -> PopUp()
                else ->
                    throw IllegalArgumentException("bad particle type: '" + part.params.getValue("type") + "'")
            }
            val emmiter = Emitter(logic,
                    part.params.getValue("framesperspawn").toInt(),
                    part.params.getValue("length").toInt()
            )

            Particles(emmiter,
                    texture = expandBrace(part.params.getValue("texture")).findFirst().get(),
                    scale = part.params.getValue("scale").toFloat(),
                    frames = part.params.getValue("length").toInt()
            )
        },
        Pair("save") { part ->
            Save(
                    file = File(part.args[0]),
                    msBtwFrames = part.params["msbtwframes"]?.toInt() ?: 0,
                    looped = part.params["looped"] != "false"
            )
        },
        Pair("scale") { part ->
            Scale(
                    xScale = part.params.getValue("x").toDouble(),
                    yScale = part.params.getValue("y").toDouble()
            )
        },
        Pair("shadow") { part ->
            Shadow(
                    radius = part.params.getValue("r").toInt(),
                    opacity = part.params.getValue("a").toFloat(),
                    xOffset = part.params.getValue("x").toInt(),
                    yOffset = part.params.getValue("y").toInt()
            )
        },
        Pair("spritesheet") { part ->
            SpriteSheet(
                    width = part.params.getValue("w").toInt(),
                    height = part.params.getValue("h").toInt(),
                    xOffset = part.params["xoffset"]?.toIntOrNull() ?: 0,
                    yOffset = part.params["yoffset"]?.toIntOrNull() ?: 0,
                    xSpace = part.params["xspace"]?.toIntOrNull() ?: 0,
                    ySpace = part.params["yspace"]?.toIntOrNull() ?: 0,
                    indices = part.params.getValue("frames").split(",").map { it.toInt() }.toList()
            )
        },
        Pair("trim") { _ ->
            Trim()
        }
)

private fun createEditor(part: Part): Editor {
    val converter = converters.getValue(part.cmd)
    return converter(part)
}