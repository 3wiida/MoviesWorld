package com.ewida.rickmorti.ui.auth.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.ewida.rickmorti.R
import com.ewida.rickmorti.common.Common.TAG
import com.ewida.rickmorti.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        watchEmailEditText()
        watchPasswordEditText()

    }

    private fun watchEmailEditText(){
        binding.emailEt.inputEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if(it.contains('@')){
                        binding.emailEt.changeDrawableVisibility(true)
                    }else{
                        binding.emailEt.changeDrawableVisibility(false)
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun watchPasswordEditText(){
        binding.passwordEt.inputEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if(p0.isNotEmpty()){
                        binding.passwordEt.changeDrawableVisibility(true)
                    }else{
                        binding.passwordEt.changeDrawableVisibility(false)
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {}

        })
    }



}