package com.alex_malishev.presentation_layer.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.alex_malishev.presentation_layer.R
import com.alex_malishev.presentation_layer.common.observe
import com.alex_malishev.presentation_layer.ui.MainActivity
import com.alex_malishev.presentation_layer.ui.base.BaseActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        Handler().postDelayed({
            startActivity(MainActivity.getMainIntent(this))
            finish()
        }, 1000)
    }
}