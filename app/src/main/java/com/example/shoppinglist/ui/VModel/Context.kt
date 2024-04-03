package com.example.shoppinglist.ui.VModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.shoppinglist.App
import com.example.shoppinglist.ViewMFactory

inline fun <T> Fragment.observe(data: LiveData<T>, crossinline callback: (T) -> Unit){
    data.observe(viewLifecycleOwner, Observer{event -> event?.let{callback(it)} })
}

inline val Fragment.factory: ViewMFactory
    get() = (requireActivity().application as App).factory
