package com.example.testformangofzco.utils

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class AuthSharedPrefs(private val activity: Activity) {

    private val prefs = activity.getSharedPreferences("auth", Context.MODE_PRIVATE)
    private val isAuth = prefs.getBoolean("is_user_auth", false)
    private val editor = prefs.edit()

    fun checkAuth(appCompatActivity: AppCompatActivity) {
        if (!isAuth) return
        activity.replaceActivity(activity = appCompatActivity)
    }

    fun logIn(appCompatActivity: AppCompatActivity) {
        editor.putBoolean("is_user_auth", true)
        editor.apply()
        activity.replaceActivity(activity = appCompatActivity)
    }

    fun logOut(appCompatActivity: AppCompatActivity) {
        editor.putBoolean("is_user_auth", false)
        editor.apply()
        activity.replaceActivity(activity = appCompatActivity)
    }
}