package com.ewida.rickmorti.ui.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.ewida.rickmorti.R
import com.ewida.rickmorti.databinding.ActivityWebBinding
import com.ewida.rickmorti.utils.bundle_utils.BundleKeys.WEB_PAGE_URL
import com.ewida.rickmorti.utils.bundle_utils.BundleUtils.getFromBundle
import com.ewida.rickmorti.utils.bundle_utils.IntentExtraKeys.AUTH_PERMISSIONS
import com.ewida.rickmorti.utils.bundle_utils.IntentExtraKeys.SIGN_UP
import javax.inject.Inject

class WebActivity : AppCompatActivity() {

    private lateinit var binding:ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.movieWorldWv.webViewClient= WebViewClient()
        val url=intent.getStringExtra(SIGN_UP)
        url?.let {
            viewWebPage(it)
        }
    }

    private fun viewWebPage(url:String){
        binding.movieWorldWv.loadUrl(url)
    }
}