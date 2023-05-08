package com.ewida.rickmorti.custom_view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ewida.rickmorti.R
import com.ewida.rickmorti.databinding.CreateListBottomSheetBinding

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CreateListBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: CreateListBottomSheetBinding
    var onCreateBtnClick: ((name: String, description: String) -> Unit)? = null

    init {
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.create_list_bottom_sheet, container, false)
        binding.btnCreateList.setOnClickListener {
            binding.btnCreateList.changeLoading(1)
            onCreateBtnClick?.invoke(
                binding.listNameET.text.toString(),
                binding.listDescriptionET.text.toString()
            )
        }
        return binding.root
    }
}