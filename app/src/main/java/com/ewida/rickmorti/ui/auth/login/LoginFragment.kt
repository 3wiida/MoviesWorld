package com.ewida.rickmorti.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ewida.rickmorti.common.Common.TAG
import com.ewida.rickmorti.databinding.FragmentLoginBinding
import com.ewida.rickmorti.model.login_response.LoginResponse
import com.ewida.rickmorti.ui.webview.WebActivity
import com.ewida.rickmorti.utils.bundle_utils.BundleKeys.WEB_PAGE_URL
import com.ewida.rickmorti.utils.bundle_utils.BundleUtils.saveInBundle
import com.ewida.rickmorti.utils.result_wrapper.CallState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val viewModel:LoginViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        binding.viewModel=viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        watchEmailEditText()
        watchPasswordEditText()
        initMainClicks()
    }

    private fun initMainClicks(){

        binding.createAnAccTv.setOnClickListener {
            saveInBundle(WEB_PAGE_URL,"https://www.themoviedb.org/signup")
            startActivity(Intent(requireActivity(),WebActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            val username=binding.emailEt.getText()
            val password=binding.passwordEt.getText()
            if(viewModel.validateForm(username,password)){
                viewModel.login(username, password)
                collectLoginResult()
            }
            Log.d(TAG, "initMainClicks: ${viewModel.formErrors}")
        }

    }

    private fun watchEmailEditText(){
        binding.emailEt.inputEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if(it.length>2){
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
                    if(it.isNotEmpty()){
                        binding.passwordEt.changeDrawableVisibility(true)
                    }else{
                        binding.passwordEt.changeDrawableVisibility(false)
                    }
                }
            }
            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun collectLoginResult(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                viewModel.loginState.collectLatest {state->
                    when(state){
                        CallState.EmptyState -> {}
                        is CallState.FailureState -> {
                            Toast.makeText(requireActivity(), state.msg, Toast.LENGTH_SHORT).show()
                            binding.loginBtn.changeLoading(0)
                        }
                        CallState.LoadingState -> binding.loginBtn.changeLoading(1)
                        is CallState.SuccessState<*> -> {
                            val data=state.data as LoginResponse
                            Toast.makeText(requireContext(), data.success.toString(), Toast.LENGTH_SHORT).show()
                            binding.loginBtn.changeLoading(0)
                        }
                    }
                }
            }
        }
    }

}