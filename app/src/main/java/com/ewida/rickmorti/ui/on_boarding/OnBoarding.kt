package com.ewida.rickmorti.ui.on_boarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ewida.rickmorti.R
import com.ewida.rickmorti.databinding.ActivityOnBoardingBinding
import com.ewida.rickmorti.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoarding : AppCompatActivity() {
    private lateinit var binding:ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMainClicks()
    }

    private fun initMainClicks(){
        binding.getStartedBtn.setOnClickListener {
            startActivity(Intent(this,AuthActivity::class.java))
            finish()
        }
    }
}