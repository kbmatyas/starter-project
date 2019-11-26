package com.possible.demo.shared

import android.graphics.Bitmap
import android.graphics.BitmapShader
import com.squareup.picasso.Transformation
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader

class PicassoCircleTransformation : Transformation {

    override fun key(): String = NAME

    override fun transform(source: Bitmap): Bitmap {
        val size = source.width.coerceAtMost(source.height)

        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)

        squaredBitmap.recycle()
        return bitmap
    }

    companion object {
        private const val NAME = "PicassoCircleTransformation"
    }
}