package com.ewida.rickmorti.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ewida.rickmorti.R
import com.ewida.rickmorti.common.Common.TAG
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys
import com.ewida.rickmorti.utils.shared_pref_utils.PrefUtils

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.d(TAG, "onCreate: ${ PrefUtils.getFromPref(
            this,
            PrefKeys.GUEST_EXPIRE_DATE,
            ""
        )}")

    }
}