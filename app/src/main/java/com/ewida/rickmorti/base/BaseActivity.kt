package com.ewida.rickmorti.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.ewida.rickmorti.common.Common
import com.ewida.rickmorti.common.Common.TAG
import com.ewida.rickmorti.utils.result_wrapper.CallState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel>: AppCompatActivity() {
    private var _binding:VB?=null
    val binding: VB get() = _binding!!
    abstract val viewModel: VM
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

    abstract fun sendCalls()
    abstract fun setUpViews()
    protected abstract fun getViewBinding(): VB

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLog(msg: String) {
        Log.d(Common.TAG, msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=getViewBinding()
        setContentView(_binding!!.root)
        setUpViews()
        sendCalls()
    }


    override fun onDestroy() {
        _binding=null
        super.onDestroy()
    }

}