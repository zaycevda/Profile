package com.example.data.utils

import android.os.CountDownTimer

class MyCountDownTimer(
    private val onFinish: () -> Unit
) : CountDownTimer(TEN_MINUTES, ONE_SECOND) {
    override fun onTick(millisUntilFinished: Long) {}

    override fun onFinish() {
        onFinish
    }

    private companion object {
        private const val TEN_MINUTES: Long = 1000 * 60 * 10
        private const val ONE_SECOND: Long = 1000
    }
}