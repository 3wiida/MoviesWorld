package com.ewida.rickmorti.custom_view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.ewida.rickmorti.R

class LoadingDialog(context: Context):Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loading_dialog_layout)
        window?.setBackgroundDrawableResource(R.drawable.loading_dialog_background)
        setCancelable(false)
    }

}