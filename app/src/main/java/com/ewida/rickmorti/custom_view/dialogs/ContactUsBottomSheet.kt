package com.ewida.rickmorti.custom_view.dialogs

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ewida.rickmorti.R
import com.ewida.rickmorti.common.Common.FACEBOOK_APP
import com.ewida.rickmorti.common.Common.FACEBOOK_URL
import com.ewida.rickmorti.common.Common.GITHUB_URL
import com.ewida.rickmorti.common.Common.LINKEDIN_APP
import com.ewida.rickmorti.common.Common.LINKEDIN_URL
import com.ewida.rickmorti.common.Common.TELEGRAM_APP
import com.ewida.rickmorti.common.Common.TELEGRAM_ERROR_MSG
import com.ewida.rickmorti.common.Common.TWITTER_APP
import com.ewida.rickmorti.common.Common.TWITTER_URL
import com.ewida.rickmorti.common.Common.WHATSAPP
import com.ewida.rickmorti.common.Common.WHATSAPP_ERROR_MSG
import com.ewida.rickmorti.databinding.ContactUsLayoutBinding
import com.ewida.rickmorti.model.contact_model.ContactMethod
import com.ewida.rickmorti.ui.home.fragments.profile.adapters.ContactMethodsAdapter
import com.ewida.rickmorti.utils.toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ContactUsBottomSheet(
    private var adapter: ContactMethodsAdapter?,
    private val list: List<ContactMethod>
) : BottomSheetDialogFragment() {

    init {
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    private var binding: ContactUsLayoutBinding?=null
    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.contact_us_layout, container, false)
        adapter?.submitList(list)
        initClicks()
        binding?.contactMethodsRV?.adapter = adapter
        return binding?.root
    }

    private fun initClicks(){
        adapter?.onItemClick={clickedMethod->
            when(clickedMethod.id){
                1->{
                    try {
                        contact(FACEBOOK_APP)
                    }catch (e:Exception){
                        contact(FACEBOOK_URL)
                    }
                }
                2->{
                    try {
                        contact(TELEGRAM_APP)
                    }catch (e:Exception){
                        toast(TELEGRAM_ERROR_MSG)
                    }
                }
                3->{
                    try {
                        contact(LINKEDIN_APP)
                    }catch (e:Exception){
                        contact(LINKEDIN_URL)
                    }
                }
                4->{
                    try {
                        contact(TWITTER_APP)
                    }catch (e:Exception){
                        contact(TWITTER_URL)
                    }
                }
                5-> contact(GITHUB_URL)
                6->{
                    try {
                        contact(WHATSAPP)
                    }catch (e:Exception){
                        toast(WHATSAPP_ERROR_MSG)
                    }
                }
            }
        }
    }

    private fun contact(url:String){
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onDestroyView() {
        binding=null
        adapter=null
        super.onDestroyView()
    }


}