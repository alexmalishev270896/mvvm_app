package com.alex_malishev.presentation_layer.ui.saved_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alex_malishev.presentation_layer.R
import com.alex_malishev.presentation_layer.ui.base.BaseFragment

class SavedNewsFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }
}