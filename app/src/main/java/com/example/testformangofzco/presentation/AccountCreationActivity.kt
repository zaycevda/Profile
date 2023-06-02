package com.example.testformangofzco.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testformangofzco.R
import com.example.testformangofzco.utils.AuthSharedPrefs

class AccountCreationActivity : AppCompatActivity(R.layout.activity_account_creation) {

    private val sharedPreferences by lazy { AuthSharedPrefs(activity = this) }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        sharedPreferences.checkAuth(MainActivity())
    }
}