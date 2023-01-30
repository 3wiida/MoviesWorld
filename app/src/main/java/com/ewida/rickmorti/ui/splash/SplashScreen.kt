package com.ewida.rickmorti.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ewida.rickmorti.ui.auth.AuthActivity
import com.ewida.rickmorti.ui.on_boarding.OnBoarding
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.IS_FIRST_TIME
import com.ewida.rickmorti.utils.shared_pref_utils.PrefUtils.getFromPref
import com.ewida.rickmorti.utils.shared_pref_utils.PrefUtils.saveToPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val isFirstTime=getFromPref(this,IS_FIRST_TIME,true)
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            if(isFirstTime.toString().toBoolean()){
                saveToPref(this, IS_FIRST_TIME,false)
                startActivity(Intent(this, OnBoarding::class.java))
                finish()
            }else{
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        },500L)
    }
}