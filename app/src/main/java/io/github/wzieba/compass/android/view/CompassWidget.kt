package io.github.wzieba.compass.android.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.LightingColorFilter
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.RotateAnimation
import io.github.wzieba.compass.R

private const val ARROW_HEIGHT = 100
private const val ARROW_WIDTH = 50
private const val CIRCLE_STROKE_WIDTH = 15f
private const val ROTATE_ARROW_ANIMATION_DURATION = 15L

class CompassWidget @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val arrowColor: Int = ContextCompat.getColor(context, R.color.colorPrimary)
    private val arrowIcon: Drawable = ContextCompat.getDrawable(context, R.drawable.ic_long_arrow_pointing_up)
    private val paint = Paint()

    private var previousDegrees = 0.0f

    init {
        arrowIcon.colorFilter = LightingColorFilter(arrowColor, arrowColor)
        paint.style = Paint.Style.STROKE
        paint.color = arrowColor
        paint.strokeWidth = CIRCLE_STROKE_WIDTH
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val radius = (width / 2) - 10
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius.toFloat(), paint)

        arrowIcon.setBounds(
                width / 2 - ARROW_WIDTH,
                height / 2 - ARROW_HEIGHT,
                width / 2 + ARROW_WIDTH,
                height / 2 + ARROW_HEIGHT
        )
        arrowIcon.draw(canvas)
    }


    fun rotateArrow(degrees: Float) {
        if (degrees != previousDegrees) {
            val animRotate = RotateAnimation(
                    previousDegrees,
                    -degrees,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f
            )
            previousDegrees = -degrees
            animRotate.duration = ROTATE_ARROW_ANIMATION_DURATION
            animRotate.fillAfter = true
            startAnimation(animRotate)
        }

    }
}