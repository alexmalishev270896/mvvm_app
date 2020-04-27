package com.alex_malishev.presentation_layer.common

import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.alex_malishev.presentation_layer.ui.base.ControllableAppbarBehaviour
import com.google.android.material.appbar.AppBarLayout

/**
 * The method controls scroll behaviour for AppBarLayout with ControllableAppbarBehaviour.
 * Calling the method for AppBarLayout without such behaviour has no effect
 */
fun AppBarLayout.setInteractionEnabled(isEnabled: Boolean) {
    val params = layoutParams as CoordinatorLayout.LayoutParams
    (params.behavior as? ControllableAppbarBehaviour)?.isEnabled = isEnabled
    layoutParams = params
}