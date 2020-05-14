package com.aixue.shapeviewlib

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StyleableRes
import androidx.core.view.ViewCompat

object ShapeViewProxy {

    fun proxyShapeAttributes(view: View, context: Context, attrs: AttributeSet, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeView,
            defStyle, 0)

        if (!hasValue(typedArray, R.styleable.ShapeView_shape)) {
            return
        }

        val drawableList = StateListDrawable()

        if (hasValue(typedArray, R.styleable.ShapeView_pressedSolidColor)) {
            val drawablePressed = buildDrawable(typedArray)
            val pressedSolidColor = typedArray.getColor(R.styleable.ShapeView_pressedSolidColor, 0)
            drawablePressed.setColor(pressedSolidColor)
            drawableList.addState(intArrayOf(android.R.attr.state_pressed), drawablePressed)
        }
        if (hasValue(typedArray, R.styleable.ShapeView_checkedSolidColor)) {
            val drawableChecked = buildDrawable(typedArray)
            val checkedSolidColor = typedArray.getColor(R.styleable.ShapeView_checkedSolidColor, 0)
            drawableChecked.setColor(checkedSolidColor)
            drawableList.addState(intArrayOf(android.R.attr.state_checked), drawableChecked)
        }
        if (hasValue(typedArray, R.styleable.ShapeView_selectedSolidColor)) {
            val drawableDisable = buildDrawable(typedArray)
            val selectedSolidColor = typedArray.getColor(R.styleable.ShapeView_selectedSolidColor, 0)
            drawableDisable.setColor(selectedSolidColor)
            drawableList.addState(intArrayOf(android.R.attr.state_selected), drawableDisable)
        }
        if (hasValue(typedArray, R.styleable.ShapeView_disableSolidColor)) {
            val drawableDisable = buildDrawable(typedArray)
            val disableSolidColor = typedArray.getColor(R.styleable.ShapeView_disableSolidColor, 0)
            drawableDisable.setColor(disableSolidColor)
            drawableList.addState(intArrayOf(-android.R.attr.state_enabled), drawableDisable)
        }
        val drawableNormal = buildDrawable(typedArray)
        drawableList.addState(intArrayOf(), drawableNormal)

        typedArray.recycle()

        ViewCompat.setBackground(view, drawableList)
    }

    private fun buildDrawable(typedArray: TypedArray): GradientDrawable {

        val shape = typedArray.getInt(R.styleable.ShapeView_shape, 0)

        val drawable = GradientDrawable()

        val cornersRadius = typedArray.getDimension(R.styleable.ShapeView_cornersRadius, 0f)
        val cornersTopLeftRadius = typedArray.getDimension(R.styleable.ShapeView_cornersTopLeftRadius,
            0f)
        val cornersTopRightRadius = typedArray.getDimension(R.styleable.ShapeView_cornersTopRightRadius,
            0f)
        val cornersBottomLeftRadius = typedArray.getDimension(R.styleable.ShapeView_cornersBottomLeftRadius,
            0f)
        val cornersBottomRightRadius = typedArray.getDimension(R.styleable.ShapeView_cornersBottomRightRadius,
            0f)

        val strokeWidth = typedArray.getDimension(R.styleable.ShapeView_strokeShapeWidth, 0f)
        val strokeColor = typedArray.getColor(R.styleable.ShapeView_strokeShapeColor, 0)
        val strokeDashGap = typedArray.getDimension(R.styleable.ShapeView_strokeDashGap, 0f)
        val strokeDashWidth = typedArray.getDimension(R.styleable.ShapeView_strokeDashWidth, 0f)

        if (hasValue(strokeWidth) && hasValue(typedArray, R.styleable.ShapeView_strokeShapeColor)) {
            if (hasValue(strokeDashGap) || hasValue(strokeDashWidth)) {
                drawable.setStroke(Math.round(strokeWidth), strokeColor, strokeDashWidth, strokeDashGap)
            } else {
                drawable.setStroke(Math.round(strokeWidth), strokeColor)
            }
        }

        val solidColor = typedArray.getColor(R.styleable.ShapeView_solidColor, 0)

        if (hasValue(typedArray, R.styleable.ShapeView_solidColor)) {
            drawable.setColor(solidColor)
        }

        setGradient(drawable, typedArray)

        setShape(drawable, shape)
        if (hasValue(cornersRadius)) {
            drawable.cornerRadius = cornersRadius
        } else if (hasValue(cornersTopLeftRadius) ||
            hasValue(cornersTopRightRadius) ||
            hasValue(cornersBottomLeftRadius) ||
            hasValue(cornersBottomRightRadius)) {
            drawable.cornerRadii = floatArrayOf(cornersTopLeftRadius, cornersTopLeftRadius,
                cornersTopRightRadius, cornersTopRightRadius, cornersBottomRightRadius, cornersBottomRightRadius,
                cornersBottomLeftRadius, cornersBottomLeftRadius)
        }
        return drawable
    }

    private fun setGradient(drawable: GradientDrawable, typedArray: TypedArray) {
        val gradientArray = ArrayList<Int>()
        if (hasValue(typedArray, R.styleable.ShapeView_gradientStartColor)) {
            gradientArray.add(typedArray.getColor(R.styleable.ShapeView_gradientStartColor, 0))
        }
        if (hasValue(typedArray, R.styleable.ShapeView_gradientCenterColor)) {
            gradientArray.add(typedArray.getColor(R.styleable.ShapeView_gradientCenterColor, 0))
        }
        if (hasValue(typedArray, R.styleable.ShapeView_gradientEndColor)) {
            gradientArray.add(typedArray.getColor(R.styleable.ShapeView_gradientEndColor, 0))
        }

        if (gradientArray.isNotEmpty()) {
            drawable.colors = gradientArray.toIntArray()
            drawable.orientation = when (typedArray.getInt(R.styleable.ShapeView_gradientOrientation, 0)) {
                0 -> GradientDrawable.Orientation.LEFT_RIGHT
                45 -> GradientDrawable.Orientation.BL_TR
                90 -> GradientDrawable.Orientation.BOTTOM_TOP
                135 -> GradientDrawable.Orientation.BR_TL
                180 -> GradientDrawable.Orientation.RIGHT_LEFT
                225 -> GradientDrawable.Orientation.TR_BL
                270 -> GradientDrawable.Orientation.TOP_BOTTOM
                315 -> GradientDrawable.Orientation.TL_BR
                else -> GradientDrawable.Orientation.TOP_BOTTOM
            }
        }
    }

    private fun hasValue(value: Float): Boolean {
        return value > 0
    }

    private fun hasValue(typedArray: TypedArray, @StyleableRes res: Int): Boolean {
        return typedArray.hasValue(res)
    }

    private fun setShape(drawable: GradientDrawable, shape: Int) {
        when (shape) {
            GradientDrawable.RECTANGLE -> drawable.shape = GradientDrawable.RECTANGLE
            GradientDrawable.OVAL -> drawable.shape = GradientDrawable.OVAL
            GradientDrawable.LINE -> drawable.shape = GradientDrawable.LINE
            GradientDrawable.RING -> drawable.shape = GradientDrawable.RING
        }
    }
}