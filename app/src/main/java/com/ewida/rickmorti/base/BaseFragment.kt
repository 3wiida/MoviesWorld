package com.ewida.rickmorti.base

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ewida.rickmorti.R
import com.ewida.rickmorti.common.Common.TAG
import com.ewida.rickmorti.utils.result_wrapper.CallState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

open class BaseFragment : Fragment() {
    fun <T> collectState(
        stateFlow: StateFlow<CallState>,
        state: Lifecycle.State,
        loading: () -> Unit,
        failure: (msg: String, code: Int) -> Unit,
        success: (T) -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(state) {
                stateFlow.collectLatest {
                    when (it) {
                        CallState.EmptyState -> {}
                        is CallState.FailureState -> failure(it.msg!!, it.code!!)
                        CallState.LoadingState -> loading()
                        is CallState.SuccessState<*> -> success(it.data as T)
                    }
                }
            }
        }
    }

    class NetworkObserver(context: Context) {
        private val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        fun observe() = callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { trySend(Status.Available) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { trySend(Status.Unavailable) }
                }


                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { trySend(Status.Losing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { trySend(Status.Lost) }
                }
            }
            cm.registerDefaultNetworkCallback(callback)
            awaitClose { cm.unregisterNetworkCallback(callback) }
        }.distinctUntilChanged()

        enum class Status {
            Available,
            Unavailable,
            Losing,
            Lost
        }
    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLog(msg: String) {
        Log.d(TAG, msg)
    }
}