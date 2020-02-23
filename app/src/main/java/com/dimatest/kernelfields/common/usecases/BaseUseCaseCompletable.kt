package com.dimatest.kernelfields.common.usecases

import com.dimatest.kernelfields.utils.ErrorHandler
import com.dimatest.kernelfields.utils.SingleLiveEvent
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.observers.DisposableCompletableObserver

abstract class BaseUseCaseCompletable<Input> (
    private val mainThread: Scheduler,
    private val ioThread: Scheduler,
    private val errorHandler: ErrorHandler
) {

    val isLoading = SingleLiveEvent<Boolean>()

    internal abstract fun buildUseCaseObservable(params: Input): Completable

    fun execute(params: Input, observer: DisposableCompletableObserver): DisposableCompletableObserver {
        val observable = this
            .buildUseCaseObservable(params)
            .subscribeOn(ioThread)
            .observeOn(mainThread)
            .doOnSubscribe { isLoading.postValue(true) }
            .doOnTerminate { isLoading.postValue(false) }
            .doOnDispose { isLoading.postValue(false) }
            .onErrorResumeNext { e: Throwable ->
                errorHandler.processError(e)
                Completable.error(e)
            }
        return observable.subscribeWith(observer)
    }

    fun execute(
        params: Input,
        onComplete: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ): DisposableCompletableObserver {
        return execute(params, object : DisposableCompletableObserver() {
            override fun onComplete() {
                onComplete?.invoke()            }

            override fun onError(e: Throwable) {
                onError?.invoke(e)            }
        })
    }

}