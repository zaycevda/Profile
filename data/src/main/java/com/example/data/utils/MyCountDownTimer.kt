package com.example.data.utils

import android.os.CountDownTimer

class MyCountDownTimer(
    private val onFinish: () -> Unit
) : CountDownTimer(TOKEN_LIFESPAN, COUNT_DOWN_INTERVAL) {
    override fun onTick(millisUntilFinished: Long) {}

    override fun onFinish() {
        onFinish
    }

    private companion object {
        private const val TOKEN_LIFESPAN: Long = 1000 * 60 * 10 // 10 minutes
        private const val COUNT_DOWN_INTERVAL: Long = 1000 // 1 second
    }
}