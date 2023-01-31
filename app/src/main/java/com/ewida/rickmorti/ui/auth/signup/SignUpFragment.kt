package com.ewida.rickmorti.ui.auth.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ewida.rickmorti.R
import com.ewida.rickmorti.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {
    lateinit var binding:FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMainClicks()
        watchEmailEditText()
        watchPasswordEditText()
        watchConfirmPasswordEditText()

    }

    private fun initMainClicks(){
        binding.loginMyAccTv.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    private fun watchEmailEditText(){
        binding.emailEt.inputEt.addTextChangedListener(object : TextWatcher {
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
        binding.passwordEt.inputEt.addTextChangedListener(object : TextWatcher {
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

    private fun watchConfirmPasswordEditText(){
        binding.confirmPasswordEt.inputEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if(p0.isNotEmpty()){
                        binding.confirmPasswordEt.changeDrawableVisibility(true)
                    }else{
                        binding.confirmPasswordEt.changeDrawableVisibility(false)
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {}

        })
    }

}