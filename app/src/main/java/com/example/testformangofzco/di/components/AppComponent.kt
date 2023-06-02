package com.example.testformangofzco.di.components

import com.example.testformangofzco.di.modules.AppModule
import com.example.testformangofzco.di.modules.DataModule
import com.example.testformangofzco.di.modules.DomainModule
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        DomainModule::class
    ]
)
interface AppComponent