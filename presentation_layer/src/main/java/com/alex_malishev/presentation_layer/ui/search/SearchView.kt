package com.alex_malishev.presentation_layer.ui.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.cardview.widget.CardView
import androidx.core.widget.doAfterTextChanged
import com.alex_malishev.presentation_layer.R
import com.alex_malishev.presentation_layer.utils.ViewUtil
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.view_search.view.*


class SearchView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attributeSet, defStyleAttr) {

    val textChangedEvent: Flowable<String> by lazy {
        Flowable.create<String>({ emitter ->
            val textChangedListener = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    p0?.let { if (!emitter.isCancelled) emitter.onNext(it.toString()) }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
            }
            textField.addTextChangedListener(textChangedListener)

            emitter.setCancellable {
                textField.removeTextChangedListener(textChangedListener)
            }

        }, BackpressureStrategy.LATEST)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_search, this, true)
        radius = ViewUtil.dpToPx(10)
        cardElevation = ViewUtil.dpToPx(6)

        textField.doAfterTextChanged {
            it?.let {
                clearButton.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }

        textField.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                v.clearFocus()
            }
            true
        }
        textField.setOnFocusChangeListener { view, b ->
            if (!b) hideKeyboard(view)
        }
        clearButton.visibility = View.GONE
        clearButton.setOnClickListener {
            textField.setText("", false)
            textField.clearFocus()
        }
        hideProgress()
    }

    fun showProgress(){
        searchProgressBar.visibility = View.VISIBLE
    }

    fun hideProgress(){
        searchProgressBar.visibility = View.GONE
    }

    private fun hideKeyboard(v: View) {
        val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
}