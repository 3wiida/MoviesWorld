package com.ewida.rickmorti.ui.home.fragments.lists

import android.content.Context
import com.ewida.rickmorti.api.ApiCalls
import com.ewida.rickmorti.utils.result_wrapper.sendSafeApiCall
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.USER_SESSION_ID
import com.ewida.rickmorti.utils.shared_pref_utils.PrefUtils.getFromPref
import javax.inject.Inject

class ListsRepository @Inject constructor(private val apiCalls: ApiCalls) {

    suspend fun createList(sessionId: String, name: String, description: String) = sendSafeApiCall {
        apiCalls.createList(
            session_id = sessionId,
            name = name,
            description = description
        )
    }

    suspend fun getLists(accountId: Int, sessionId: String) = sendSafeApiCall {
        apiCalls.getCreatedLists(account_id = accountId, session_id = sessionId)
    }

    suspend fun deleteList(listId: String, sessionId: String) = sendSafeApiCall {
        apiCalls.deleteList(list_id = listId, session_id = sessionId)
    }
}