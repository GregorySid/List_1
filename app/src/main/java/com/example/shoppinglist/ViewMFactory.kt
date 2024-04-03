package com.example.shoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.ui.Room.MainDb
import com.example.shoppinglist.ui.VModel.DashboardViewModel
import com.example.shoppinglist.ui.VModel.Repo
import com.example.shoppinglist.ui.home.HomeViewModel

class ViewMFactory(private val appDb: MainDb): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass){
            DashboardViewModel::class.java -> DashboardViewModel(appDb.getDao())
            HomeViewModel::class.java -> HomeViewModel(appDb.getDao(), Repo(appDb))
            else -> throw IllegalAccessException("Cannot fild $modelClass")
        } as T
    }
}
