package com.ewida.rickmorti.ui.home.fragments.lists

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.viewModelScope
import com.ewida.rickmorti.base.BaseViewModel
import com.ewida.rickmorti.common.Common.ACCOUNT_ID
import com.ewida.rickmorti.model.created_lists_response.CreatedLists
import com.ewida.rickmorti.utils.result_wrapper.CallResult
import com.ewida.rickmorti.utils.result_wrapper.CallState
import com.ewida.rickmorti.utils.shared_pref_utils.PrefKeys.USER_SESSION_ID
import com.ewida.rickmorti.utils.shared_pref_utils.PrefUtils.getFromPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(private val repo: ListsRepository) : BaseViewModel() {

    private val _createListState = MutableStateFlow<CallState>(CallState.EmptyState)
    val createListState = _createListState.asStateFlow()

    private val _getListsState = MutableStateFlow<CallState>(CallState.EmptyState)
    val getListsState = _getListsState.asStateFlow()

    private val _deleteListState = MutableStateFlow<CallState>(CallState.EmptyState)
    val deleteListState = _deleteListState.asStateFlow()

    val isLoading = ObservableBoolean(true)

    val isGuestBoolean = ObservableBoolean(false)

    val isEmptyState = ObservableBoolean(false)

    init {
        isGuestBoolean.set(ACCOUNT_ID == -1)
    }

    fun getLists(context: Context) {
        _getListsState.value = CallState.EmptyState
        viewModelScope.launch(Dispatchers.IO) {
            when (val response =
                repo.getLists(accountId = ACCOUNT_ID, sessionId = getSessionId(context))) {
                is CallResult.CallFailure -> {
                    isLoading.set(false)
                    _getListsState.value = CallState.FailureState(response.msg, response.code)
                }

                is CallResult.CallSuccess -> {
                    isLoading.set(false)
                    isEmptyState.set(response.data.total_results == 0)
                    _getListsState.value = CallState.SuccessState(response.data)
                }
            }
        }
    }


    fun createList(context: Context, name: String, description: String) {
        _createListState.value = CallState.EmptyState
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repo.createList(
                sessionId = getSessionId(context),
                name = name,
                description = description
            )) {
                is CallResult.CallFailure -> _createListState.value =
                    CallState.FailureState(msg = response.msg, code = response.code)

                is CallResult.CallSuccess -> {
                    isEmptyState.set(false)
                    _createListState.value = CallState.SuccessState(data = response.data)
                }
            }
        }
    }

    fun deleteList(context: Context, listId: String) {
        _deleteListState.value = CallState.EmptyState
        viewModelScope.launch(Dispatchers.IO) {
            _deleteListState.value = CallState.LoadingState
            when (val response =
                repo.deleteList(listId = listId, sessionId = getSessionId(context))) {
                is CallResult.CallFailure -> _deleteListState.value =
                    CallState.FailureState(response.msg, response.code)

                is CallResult.CallSuccess ->
                    _deleteListState.value = CallState.SuccessState(response.data)

            }
        }
    }

    private fun getSessionId(context: Context) =
        getFromPref(context = context, key = USER_SESSION_ID, defValue = "").toString()

    fun generateListAfterDelete(
        oldList: List<CreatedLists>,
        deletedItem: CreatedLists
    ): List<CreatedLists> {
        val newList = oldList.map { it.copy() }.toMutableList()
        newList.remove(deletedItem)
        return newList
    }

    fun generateListAfterCreate(
        oldList: List<CreatedLists>,
        createdItem: CreatedLists
    ): List<CreatedLists> {
        val newList = oldList.map { it.copy() }.toMutableList()
        newList.add(createdItem)
        return newList
    }

    fun searchList(sourceList: List<CreatedLists>, searchQuery: String): List<CreatedLists> {
        val resultsList = mutableListOf<CreatedLists>()
        sourceList.forEach { item ->
            if (item.name.contains(searchQuery) || item.description.contains(searchQuery)) {
                resultsList.add(item)
            }
        }
        return resultsList
    }

    fun isGuest() = ACCOUNT_ID == -1
}