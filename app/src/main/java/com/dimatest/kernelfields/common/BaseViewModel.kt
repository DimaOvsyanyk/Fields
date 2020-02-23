package com.dimatest.kernelfields.common

import androidx.lifecycle.ViewModel
import com.dimatest.kernelfields.utils.SingleMediatorEvent
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val disposable = CompositeDisposable()
    val loading = SingleMediatorEvent<Boolean>()

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}