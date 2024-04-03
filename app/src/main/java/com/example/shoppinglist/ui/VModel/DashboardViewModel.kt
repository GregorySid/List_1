package com.example.shoppinglist.ui.VModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.ui.Room.Dao
import com.example.shoppinglist.ui.Room.Item
import kotlinx.coroutines.launch
import java.util.Date

class DashboardViewModel(private val dao: Dao) : ViewModel() {
    private var name: String = ""
    private var notifyTime: Date = Date()
    private var notifyEnabl = false
    private var mResult: MutableLiveData<Result> = SingleLiveEvent<Result>()
    private var timeCheck = MutableLiveData<Boolean>()
    private var saveEnabled = MutableLiveData<Boolean>(false)
    private var mdateTime = MutableLiveData(notifyTime)
    val timChe: LiveData<Boolean> = timeCheck
    val result: LiveData<Result> = mResult
    val save: LiveData<Boolean> = saveEnabled
    val dateTime: LiveData<Date> = mdateTime


    fun onNameChanged(text: CharSequence?) {
        name = text?.toString() ?: ""
        saveEnabled.value = !text.isNullOrEmpty()
    }

    fun onTimeCheckedChange(checked: Boolean) {
        timeCheck.value = checked
        notifyEnabl = checked
    }

    fun OnDateChanged(date: Date) {
        notifyTime = date
        mdateTime.value = date
    }

    fun onSaveClick() {
        viewModelScope.launch{
            val id = dao.insItem(
                Item(name,
                    notifyEnabl,
                    if(notifyEnabl) notifyTime.time else null))
            mResult.value =
                Result.Save(id.toInt(),
                    name,
                    if(notifyEnabl) notifyTime.time else null)
            }
        }

    fun onCancaleClick() {
        mResult.value = Result.Cancel
    }
}

sealed class Result {
    object Cancel: Result()
    data class Save(val id: Int, val name: String, val time: Long?): Result()
}