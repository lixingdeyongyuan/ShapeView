package com.aixue.shapeviewlib

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

open class ShapeRelativeLayout @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attributes, defStyleAttr) {

    init {
        attributes?.let {
            ShapeViewProxy.proxyShapeAttributes(this, context, it, defStyleAttr)
        }
    }

}