package com.ewida.rickmorti.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.ewida.rickmorti.common.Common.TAG
import com.ewida.rickmorti.utils.result_wrapper.CallState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {
    private var _binding:VB?=null
    val binding: VB get() = _binding!!
    abstract val viewModel: VM

    fun <T> collectState(
        stateFlow: StateFlow<CallState>,
        state: Lifecycle.State=Lifecycle.State.CREATED,
        loading: () -> Unit={},
        failure: ((msg: String, code: Int) -> Unit)={_,_->},
        success: (T) -> Unit={}
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(state) {
                stateFlow.collectLatest {
                    when (it) {
                        is CallState.EmptyState -> {}
                        is CallState.LoadingState -> loading()
                        is CallState.FailureState -> failure(it.msg!!, it.code!!)
                        is CallState.SuccessState<*> -> success(it.data as T)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sendCalls()
        collectResults()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        setUpViews()
        initClicks()
        startObservers()
        return binding.root
    }

    open fun sendCalls(){}
    open fun setUpViews(){}
    open fun collectResults(){}
    open fun initClicks(){}
    open fun startObservers(){}

    protected abstract fun getViewBinding(): VB

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLog(msg: String) {
        Log.d(TAG, msg)
    }

    override fun onDestroyView() {
        _binding=null
        super.onDestroyView()
    }
}