package com.example.testformangofzco

import android.app.Application
import com.example.testformangofzco.di.components.AppComponent
import com.example.testformangofzco.di.components.DaggerAppComponent
import com.example.testformangofzco.di.modules.AppModule
import com.example.testformangofzco.di.modules.DataModule
import com.example.testformangofzco.di.modules.DomainModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .dataModule(DataModule())
            .domainModule(DomainModule())
            .build()
    }

    companion object {
        lateinit var component: AppComponent
            private set
    }
}