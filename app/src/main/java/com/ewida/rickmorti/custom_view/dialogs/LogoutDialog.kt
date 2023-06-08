package com.ewida.rickmorti.custom_view.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Window
import com.ewida.rickmorti.R
import com.ewida.rickmorti.custom_view.MainBtn
import com.ewida.rickmorti.ui.auth.AuthActivity
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.REMEMBER_ME_VALUE
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.USER_SESSION_ID
import com.ewida.rickmorti.utils.shared_pref_utils.PrefUtils.saveToPref

class LogoutDialog(private val context:Context,private var activity:Activity?=null):Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.logout_dialog_layout)
        window?.setBackgroundDrawableResource(R.drawable.loading_dialog_background)
        setCancelable(true)
        initClicks()
    }

    private fun initClicks(){
        this.findViewById<MainBtn>(R.id.logoutBtn).setOnClickListener {
            val intent=Intent(activity,AuthActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            saveToPref(context,REMEMBER_ME_VALUE,false)
            saveToPref(context,USER_SESSION_ID,-1)
        }

        this.findViewById<MainBtn>(R.id.cancelBtn).setOnClickListener {
            this.dismiss()
        }
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
        super.setOnDismissListener(listener)
        activity=null
    }
}