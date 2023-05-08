package com.ewida.rickmorti.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.ewida.rickmorti.base.BaseFragment
import com.ewida.rickmorti.databinding.FragmentLoginBinding
import com.ewida.rickmorti.model.guest_session_response.GuestSessionResponse
import com.ewida.rickmorti.model.user_login_session.UserSessionResponse
import com.ewida.rickmorti.ui.home.HomeActivity
import com.ewida.rickmorti.ui.webview.WebActivity
import com.ewida.rickmorti.utils.bundle_utils.IntentExtraKeys.SIGN_UP
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.LAST_LOGIN_WAS
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.REMEMBER_ME_VALUE
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.USER_SESSION_ID
import com.ewida.rickmorti.utils.shared_pref_utils.PrefUtils.getFromPref
import com.ewida.rickmorti.utils.shared_pref_utils.PrefUtils.saveToPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding,LoginViewModel>() {

    /** Vars **/
    override val viewModel: LoginViewModel by viewModels()

    /** Functions **/
    override fun sendCalls() {}
    override fun setUpViews() {
        binding.viewModel=viewModel
        initMainClicks()
    }

    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectState<UserSessionResponse>(
            viewModel.loginState,
            Lifecycle.State.CREATED,
            { UserLoginCollector().loading() },
            {msg,_ ->  UserLoginCollector().failure(msg)},
            {UserLoginCollector().success(it)}
        )

        collectState<GuestSessionResponse>(
            viewModel.guestSessionResponse,
            Lifecycle.State.CREATED,
            { GuestLoginCollector().loading() },
            { msg,_ -> GuestLoginCollector().failure(msg) },
            { GuestLoginCollector().success(it) }
        )

    }

    private fun initMainClicks() {
        binding.createAnAccTv.setOnClickListener {
            val intent=Intent(requireActivity(),WebActivity::class.java)
            intent.putExtra(SIGN_UP,"https://www.themoviedb.org/signup")
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            val username = binding.emailEt.getText()
            val password = binding.passwordEt.getText()
            if (viewModel.validateForm(username, password)) {
                viewModel.login(username, password)
            }
        }

        binding.guestLoginBtn.setOnClickListener {
            beginGuestSession()
        }
    }

    private fun beginGuestSession(){
        val guestSessionId = getFromPref(requireContext(), USER_SESSION_ID,"") as String
        if(guestSessionId.isEmpty()){
            viewModel.createGuestSession()
        }else{
            binding.guestLogo.visibility = View.INVISIBLE
            binding.guestBtnTxt.visibility = View.INVISIBLE
            binding.guestBtnPb.visibility = View.VISIBLE
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            activity?.finish()
            binding.guestLogo.visibility = View.VISIBLE
            binding.guestBtnTxt.visibility = View.VISIBLE
            binding.guestBtnPb.visibility = View.INVISIBLE
        }
    }

    /** Collectors **/
    private inner class UserLoginCollector {
        fun loading() {
            binding.loginBtn.changeLoading(1)
        }

        fun failure(msg: String) {
            binding.loginBtn.changeLoading(0)
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
        }

        fun success(data: UserSessionResponse) {
            binding.loginBtn.changeLoading(0)
            val rememberMe=binding.rememberMeCheckBox.isChecked
            saveToPref(requireContext(), REMEMBER_ME_VALUE,rememberMe)
            saveToPref(requireContext(),USER_SESSION_ID,data.session_id)
            saveToPref(requireContext(), LAST_LOGIN_WAS,"user")
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            activity?.finish()
        }
    }
    private inner class GuestLoginCollector {
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
            saveToPref(requireContext(), USER_SESSION_ID,data.guest_session_id)
            //saveToPref(requireContext(),GUEST_EXPIRE_DATE,data.expires_at)
            saveToPref(requireContext(), LAST_LOGIN_WAS,"guest")
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            activity?.finish()
        }
    }

}