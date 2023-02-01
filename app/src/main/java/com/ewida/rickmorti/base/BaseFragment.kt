package com.ewida.rickmorti.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ewida.rickmorti.utils.result_wrapper.CallState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class BaseFragment:Fragment() {

    fun <T> collectState(
        stateFlow: StateFlow<CallState>,
        state: Lifecycle.State,
        loading: () -> Unit,
        failure: (msg:String,code:Int) -> Unit,
        success: (T) -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle (state) {
                stateFlow.collectLatest {
                    when(it){
                        CallState.EmptyState -> {}
                        is CallState.FailureState -> failure(it.msg!!,it.code!!)
                        CallState.LoadingState -> loading()
                        is CallState.SuccessState<*> -> success(it.data as T)
                    }
                }
            }
        }
    }
}