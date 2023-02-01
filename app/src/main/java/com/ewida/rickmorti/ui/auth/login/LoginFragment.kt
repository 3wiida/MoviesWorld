package com.ewida.rickmorti.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.ewida.rickmorti.base.BaseFragment
import com.ewida.rickmorti.databinding.FragmentLoginBinding
import com.ewida.rickmorti.model.guest_session_response.GuestSessionResponse
import com.ewida.rickmorti.model.login_response.LoginResponse
import com.ewida.rickmorti.ui.webview.WebActivity
import com.ewida.rickmorti.utils.bundle_utils.BundleKeys.WEB_PAGE_URL
import com.ewida.rickmorti.utils.bundle_utils.BundleUtils.saveInBundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        watchEmailEditText()
        watchPasswordEditText()
        initMainClicks()
    }

    private fun initMainClicks() {

        binding.createAnAccTv.setOnClickListener {
            saveInBundle(WEB_PAGE_URL, "https://www.themoviedb.org/signup")
            startActivity(Intent(requireActivity(), WebActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            val username = binding.emailEt.getText()
            val password = binding.passwordEt.getText()
            if (viewModel.validateForm(username, password)) {
                viewModel.login(username, password)
                collectState<LoginResponse>(
                    viewModel.loginState,
                    Lifecycle.State.STARTED,
                    { UserLoginState().loading() },
                    {msg,_ ->  UserLoginState().failure(msg)},
                    {UserLoginState().success(it)}
                )
            }
        }

        binding.guestLoginBtn.setOnClickListener {
            viewModel.createGuestSession()
            collectState<GuestSessionResponse>(
                viewModel.guestSessionResponse,
                Lifecycle.State.STARTED,
                { GuestSessionState().loading() },
                { msg,_ -> GuestSessionState().failure(msg) },
                { GuestSessionState().success(it) }
            )
        }

    }

    private fun watchEmailEditText() {
        binding.emailEt.inputEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if (it.length > 2) {
                        binding.emailEt.changeDrawableVisibility(true)
                    } else {
                        binding.emailEt.changeDrawableVisibility(false)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun watchPasswordEditText() {
        binding.passwordEt.inputEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if (it.isNotEmpty()) {
                        binding.passwordEt.changeDrawableVisibility(true)
                    } else {
                        binding.passwordEt.changeDrawableVisibility(false)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private inner class GuestSessionState {
        fun loading() {
            binding.guestLogo.visibility = View.INVISIBLE
            binding.guestBtnTxt.visibility = View.INVISIBLE
            binding.guestBtnPb.visibility = View.VISIBLE
        }

        private fun disableLoading() {
            binding.guestLogo.visibility = View.VISIBLE
            binding.guestBtnTxt.visibility = View.VISIBLE
            binding.guestBtnPb.visibility = View.INVISIBLE
        }

        fun failure(msg: String) {
            disableLoading()
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
        }

        fun success(data: GuestSessionResponse) {
            disableLoading()
            Toast.makeText(requireActivity(), data.guest_session_id, Toast.LENGTH_SHORT).show()
        }
    }

    private inner class UserLoginState {
        fun loading() {
            binding.loginBtn.changeLoading(1)
        }

        fun failure(msg: String) {
            binding.loginBtn.changeLoading(0)
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
        }

        fun success(data: LoginResponse) {
            binding.loginBtn.changeLoading(0)
            Toast.makeText(requireContext(), data.success.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}