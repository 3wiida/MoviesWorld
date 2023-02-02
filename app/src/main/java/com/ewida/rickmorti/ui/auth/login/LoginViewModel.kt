package com.ewida.rickmorti.ui.auth.login

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewida.rickmorti.utils.form_validator.FormValidator
import com.ewida.rickmorti.utils.result_wrapper.CallResult
import com.ewida.rickmorti.utils.result_wrapper.CallState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repo: LoginRepository) : ViewModel() {

    //Variables
    private val _loginState = MutableStateFlow<CallState>(CallState.EmptyState)
    val loginState = _loginState.asStateFlow()

    private val _guestSessionResponse = MutableStateFlow<CallState>(CallState.EmptyState)
    val guestSessionResponse = _guestSessionResponse.asStateFlow()

    val formErrors = ObservableArrayList<FormValidator.ErrorEnum>()

    //Functions
    private suspend fun getRequestToken(username: String, password: String) {
        _loginState.value = CallState.LoadingState
        when (val response = repo.requestToken()) {
            is CallResult.CallFailure -> {
                _loginState.value = CallState.FailureState(response.msg, response.code)
            }
            is CallResult.CallSuccess -> {
                loginUser(username, password, response.data.request_token)
            }
        }
    }

    private suspend fun loginUser(username: String, password: String, request_token: String) {
        when (val response = repo.loginUser(username, password, request_token)) {
            is CallResult.CallFailure -> _loginState.value =
                CallState.FailureState(response.msg, response.code)
            is CallResult.CallSuccess -> {
                createSessionData(response.data.request_token)
            }
        }
    }

    private suspend fun createSessionData(request_token:String){
        when(val response=repo.createUserSession(request_token)){
            is CallResult.CallFailure -> _loginState.value=CallState.FailureState(response.msg,response.code)
            is CallResult.CallSuccess -> _loginState.value=CallState.SuccessState(response.data)
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getRequestToken(username, password)
        }
    }

    fun createGuestSession(){
        viewModelScope.launch(Dispatchers.IO){
            _guestSessionResponse.value=CallState.LoadingState
            when(val response=repo.createGuestSession()){
                is CallResult.CallFailure -> _guestSessionResponse.value=CallState.FailureState(response.msg,response.code)
                is CallResult.CallSuccess -> _guestSessionResponse.value=CallState.SuccessState(response.data)
            }
        }
    }

    fun validateForm(username: String, password: String): Boolean {
        formErrors.clear()
        if (username.isEmpty())
            formErrors.add(FormValidator.ErrorEnum.INVALID_USERNAME)
        if (password.isEmpty())
            formErrors.add(FormValidator.ErrorEnum.INVALID_PASSWORD)
        return formErrors.isEmpty()
    }
}