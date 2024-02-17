package org.example.icon

import java.awt.AlphaComposite
import java.awt.Graphics2D
import java.awt.Image
import javax.imageio.ImageIO

object Renderer {
    val white: Image by lazy { load("white.png") }
    val black: Image by lazy { load("black.png") }
    val cross: Image by lazy { load("cross.png") }
    val nought: Image by lazy { load("nought.png") }

    fun load(name: String): Image {
        val resource = this::class.java.classLoader.getResourceAsStream(name)
        return ImageIO.read(resource)
    }

    fun showWhite(g2d: Graphics2D, width: Int, height: Int, isHint: Boolean) = show(g2d, white, width, height, isHint)
    fun showBlack(g2d: Graphics2D, width: Int, height: Int, isHint: Boolean) = show(g2d, black, width, height, isHint)
    fun showCross(g2d: Graphics2D, width: Int, height: Int, isHint: Boolean) = show(g2d, cross, width, height, isHint)
    fun showNought(g2d: Graphics2D, width: Int, height: Int, isHint: Boolean) = show(g2d, nought, width, height, isHint)

    private fun show(g2d: Graphics2D, image: Image, width: Int, height: Int, isHint: Boolean) {
        val size = minOf(width, height)
        val pic = image.getScaledInstance(size, size, Image.SCALE_FAST)

        val x = (width - size) / 2
        val y = (height - size) / 2
        g2d.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, if (isHint) 0.3f else 1f)
        g2d.drawImage(pic, x, y, null)
    }
}