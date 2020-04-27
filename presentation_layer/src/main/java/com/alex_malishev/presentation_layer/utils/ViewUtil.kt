package com.alex_malishev.presentation_layer.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.Window
import android.view.inputmethod.InputMethodManager
import kotlin.math.roundToInt

object ViewUtil {
    private val TAG = ViewUtil::class.java.simpleName

    fun pxToDp(px: Float): Float {
        val densityDpi =
            Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }

    fun dpToPx(dp: Int) = dp * Resources.getSystem().displayMetrics.density

    val screenWidth: Int
        get() = Resources.getSystem().displayMetrics.widthPixels

    val screenHeight: Int
        get() = Resources.getSystem().displayMetrics.heightPixels

    fun hideKeyboard(activity: Activity) {
        val imm =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
    }

    fun getActionBarHeight(activity: Activity): Int {
        val tv = TypedValue()
        return if (activity.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            TypedValue.complexToDimensionPixelSize(
                tv.data,
                activity.resources.displayMetrics
            )
        } else 0
    }

    fun getWindowHeight(window: Window?): Int {
        if (window == null) return 0
        val rectangle = Rect()
        val decor = window.decorView
        decor.getWindowVisibleDisplayFrame(rectangle)
        val statusBar = rectangle.top
        val navBar = decor.height - rectangle.bottom
        return decor.height - statusBar - navBar
    }
}