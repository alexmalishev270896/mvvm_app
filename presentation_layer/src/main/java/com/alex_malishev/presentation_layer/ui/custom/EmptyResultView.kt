package com.alex_malishev.presentation_layer.ui.custom

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.res.use
import com.alex_malishev.presentation_layer.R
import kotlinx.android.synthetic.main.view_empty_result.view.*
import kotlin.properties.Delegates

class EmptyResultView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttrs: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttrs) {

    var canTryAgain: Boolean by Delegates.observable(true){_, _, new ->
        tryAgainButton.visibility = if (new) View.VISIBLE else View.GONE
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_empty_result, this, true)

        context.theme.obtainStyledAttributes(attributeSet, R.styleable.EmptyResultView,
            0, 0).use {
            canTryAgain = it.getBoolean(R.styleable.EmptyResultView_tryAgain, true)
        }

    }

    fun setOnTryAgainClickListener(clickListener: OnClickListener) {
        tryAgainButton.setOnClickListener(clickListener)
    }

    fun show() {
        this.visibility = View.VISIBLE
        alpha = 0f
        animate()
            .alpha(1f)
            .setDuration(300)
            .start()
    }

    fun hide() {
        visibility = View.GONE
    }
}