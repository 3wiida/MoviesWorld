package com.ewida.rickmorti.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ewida.rickmorti.ui.auth.AuthActivity
import com.ewida.rickmorti.ui.home.HomeActivity
import com.ewida.rickmorti.ui.on_boarding.OnBoarding
import com.ewida.rickmorti.utils.movie_world_utils.isSessionValid
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.GUEST_EXPIRE_DATE
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.IS_FIRST_TIME
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.LAST_LOGIN_WAS
import com.ewida.rickmorti.utils.shared_pref_utils.PrefUtils.getFromPref
import com.ewida.rickmorti.utils.shared_pref_utils.PrefUtils.saveToPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isFirstTime=getFromPref(this,IS_FIRST_TIME,true) as Boolean
        val rememberMeValue = getFromPref(this, PrefKeys.REMEMBER_ME_VALUE, false) as Boolean
        val userOrGuest= getFromPref(this, LAST_LOGIN_WAS,"") as String
        val guestExpiryDate= getFromPref(this, GUEST_EXPIRE_DATE,"") as String

        Handler(Looper.getMainLooper()).postDelayed({

            if(isFirstTime){
                saveToPref(this, IS_FIRST_TIME,false)
                startActivity(Intent(this, OnBoarding::class.java))
                finish()
            }else{
                if(userOrGuest.isEmpty()){
                    startActivity(Intent(this, AuthActivity::class.java))
                    finish()
                }else if(userOrGuest=="user"){
                    if(rememberMeValue){
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }else{
                        startActivity(Intent(this, AuthActivity::class.java))
                        finish()
                    }
                }else{
                    if(isSessionValid(guestExpiryDate)){
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }else{
                        startActivity(Intent(this, AuthActivity::class.java))
                        finish()
                    }
                }

            }

        },500L)
    }


}