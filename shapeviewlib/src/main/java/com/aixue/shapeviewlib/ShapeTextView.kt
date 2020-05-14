package com.aixue.shapeviewlib

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

open class ShapeTextView @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attributes, defStyleAttr) {

    init {
        attributes?.let {
            ShapeViewProxy.proxyShapeAttributes(this, context, it, defStyleAttr)
        }
    }
}