package com.ewida.rickmorti.ui.on_boarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ewida.rickmorti.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoarding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
    }
}