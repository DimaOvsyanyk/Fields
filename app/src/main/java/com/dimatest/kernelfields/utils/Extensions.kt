package com.dimatest.kernelfields.utils

import android.view.View
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

infix fun View.click(click: () -> Unit) {
    this.setOnClickListener { click() }
}

fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun View.setVisibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun Disposable.addToDisposable(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun EditText.getTxt() = this.text.toString().trim()

fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, observer: (data: T) -> Unit) {
    if (this.hasActiveObservers())
        this.removeObservers(owner)
    this.observe(owner, Observer { it?.let { observer(it) } })
}