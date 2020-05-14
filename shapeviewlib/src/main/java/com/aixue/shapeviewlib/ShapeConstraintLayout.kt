package com.aixue.shapeviewlib

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

open class ShapeConstraintLayout @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributes, defStyleAttr) {

    init {
        attributes?.let {
            ShapeViewProxy.proxyShapeAttributes(this, context, it, defStyleAttr)
        }
    }

}