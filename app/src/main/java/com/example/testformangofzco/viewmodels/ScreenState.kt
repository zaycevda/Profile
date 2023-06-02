package com.example.testformangofzco.viewmodels

sealed class ScreenState<T> {
    private val onErrorEmpty: (Throwable) -> Unit = {}
    private val onLoadingEmpty: () -> Unit = {}
    private val onSuccessEmpty: (T) -> Unit = {}

    fun on(
        error: (Throwable) -> Unit = onErrorEmpty,
        loading: () -> Unit = onLoadingEmpty,
        success: (T) -> Unit = onSuccessEmpty
    ) {
        when (this) {
            is ErrorScreenState -> onError(doOnError = error)
            is LoadingScreenState -> loading()
            is SuccessScreenState -> success(data)
        }
    }

    class ErrorScreenState<T>(private val throwable: Throwable) : ScreenState<T>() {
        private var isShowedError = false

        fun onError(doOnError: (Throwable) -> Unit) {
            if (!isShowedError) {
                doOnError(throwable)
                isShowedError = true
            }
        }
    }

    class LoadingScreenState<T> : ScreenState<T>()
    class SuccessScreenState<T>(val data: T) : ScreenState<T>()
}