package com.duranun.snowflake

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class SnowFlakeView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val snowflakes = ArrayList<Snowflake>()
    private val paint = Paint().apply {
        color = Color.WHITE
        alpha=200
        maskFilter = BlurMaskFilter(8f,BlurMaskFilter.Blur.NORMAL)
        strokeWidth = 1f
    }
    private val maxSnowflake = 1000
    private val bounds = RectF()

    override fun onDraw(canvas: Canvas) {
        snowflakes.forEach {
            it.y += it.speed
            it.x += it.wind
            bounds.set(
                it.x,
                it.y,
                (it.x + it.size),
                (it.y + it.size)
            )
            canvas.drawOval(bounds, paint)
        }
        snowflakes.removeAll { it.y > height }
        snowflakes.removeAll { it.x > width }
        if (snowflakes.size < maxSnowflake) {
            snowflakes.add(Snowflake(width, height))
        }
        invalidate()
    }

    data class Snowflake(val width: Int, val height: Int) {
        var x = Random.nextInt(width).toFloat()
        var y = Random.nextInt(height).toFloat() - height
        var size = Random.nextFloat() * 20 + 5
        var speed = Random.nextFloat() * 3 + 1
        var wind = Random.nextInt(-2, 2).toFloat()
    }
}
