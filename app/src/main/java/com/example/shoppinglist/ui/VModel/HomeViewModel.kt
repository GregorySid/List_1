package com.example.shoppinglist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.ui.VModel.Repo
import com.example.shoppinglist.ui.HomeListItem
import com.example.shoppinglist.ui.Room.Dao
import com.example.shoppinglist.ui.VModel.SingleLiveEvent
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date

class HomeViewModel(private val dao: Dao, private val repos: Repo) : ViewModel() {
    private val mCancel: MutableLiveData<Int> = SingleLiveEvent()
    val cancel: LiveData<Int> = mCancel

    fun getHome() = dao.getItems().map {
        it.map { list ->
            with(list) {
                HomeListItem(id, name, isChecked, datatime?.let { time ->
                    android.text.format.DateFormat.format("E dd MMM HH:mm", Date(time))
                })
            }
        }
    }.asLiveData(viewModelScope.coroutineContext)

    fun onCheckH(id: Long, checked: Boolean) {
        if (checked) mCancel.value = id.toInt()
        viewModelScope.launch {
            dao.setChecked(id, checked)
        }
    }

    fun dalN(id: Long) {
        viewModelScope.launch {
            repos.dalete(id)
        }
    }
}