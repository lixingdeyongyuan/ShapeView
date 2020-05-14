package com.aixue.shapeviewlib

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class ShapeLinearLayout @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributes, defStyleAttr) {

    init {
        attributes?.let {
            ShapeViewProxy.proxyShapeAttributes(this, context, it, defStyleAttr)
        }
    }

}