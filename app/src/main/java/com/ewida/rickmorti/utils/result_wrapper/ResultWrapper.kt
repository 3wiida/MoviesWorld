package com.ewida.rickmorti.utils.result_wrapper

import com.ewida.rickmorti.model.network_error_model.ErrorModel
import com.google.gson.Gson
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException

sealed class CallResult<out T> {
    data class CallSuccess<T>(val data: T) : CallResult<T>()
    data class CallFailure(val msg: String, val code: Int) : CallResult<Nothing>()


}

sealed class CallState{
    object EmptyState:CallState()
    object LoadingState:CallState()
    data class SuccessState<T>(val data:T):CallState()
    data class FailureState(val msg: String?=null,val code: Int?=null):CallState()
}

suspend fun <T> sendSafeApiCall(apiCall: suspend () -> T): CallResult<T> {
    return try {
        CallResult.CallSuccess(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is SocketTimeoutException -> CallResult.CallFailure("Call Time Out",-2)
            is IOException -> CallResult.CallFailure("No Internet Connection", -1)
            is HttpException -> {
                val failure = getFailureBody(throwable)
                CallResult.CallFailure(failure.status_message , failure.status_code )
            }
            else -> CallResult.CallFailure(throwable.message.toString(), -2)
        }
    }
}

private fun getFailureBody(exception: HttpException): ErrorModel {
    return Gson().fromJson(exception.response()?.errorBody()?.string(), ErrorModel::class.java)
}