package com.ewida.rickmorti.custom_view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ewida.rickmorti.R
import com.ewida.rickmorti.databinding.ProfileDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileBottomSheet(private val title: String, private val content: String) :
    BottomSheetDialogFragment() {

    private var binding: ProfileDialogLayoutBinding? = null

    init {
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.profile_dialog_layout, container, false)
        binding?.title?.text = title
        binding?.content?.text = content
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}