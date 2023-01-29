package com.ewida.rickmorti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ewida.rickmorti.custom_view.MainBtn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn:MainBtn=findViewById(R.id.startbtn)
        btn.setOnClickListener {
            btn.changeLoading(1)
        }
    }
}