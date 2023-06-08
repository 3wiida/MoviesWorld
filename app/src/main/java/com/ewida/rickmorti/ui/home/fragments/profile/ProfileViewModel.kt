package com.ewida.rickmorti.ui.home.fragments.profile

import android.content.Context
import com.ewida.rickmorti.R
import com.ewida.rickmorti.base.BaseViewModel
import com.ewida.rickmorti.model.contact_model.ContactMethod
import com.ewida.rickmorti.model.profile_item.ProfileItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repo: ProfileRepository) : BaseViewModel() {
    fun getServiceList(context: Context): List<ProfileItemModel> {
        return listOf(
            ProfileItemModel(1, context.getString(R.string.termsandconditions), R.drawable.terms_ic),
            ProfileItemModel(2, context.getString(R.string.aboutus), R.drawable.about_ic),
            ProfileItemModel(3, context.getString(R.string.contactus), R.drawable.contact_ic),
            ProfileItemModel(4, context.getString(R.string.logout), R.drawable.log_out_ic)
        )
    }

    fun getContactMethodsList():List<ContactMethod>{
        return listOf(
            ContactMethod(1, R.drawable.facebook_icon),
            ContactMethod(2, R.drawable.telegram_icon),
            ContactMethod(3, R.drawable.linkedin_icon),
            ContactMethod(4, R.drawable.twitter_icon),
            ContactMethod(5, R.drawable.github_icon),
            ContactMethod(6, R.drawable.whatsapp_icon)
        )
    }
}