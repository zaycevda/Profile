package com.example.testformangofzco.di.components

import com.example.testformangofzco.di.modules.AppModule
import com.example.testformangofzco.di.modules.DataModule
import com.example.testformangofzco.di.modules.DomainModule
import com.example.testformangofzco.presentation.AuthorizationFragment
import com.example.testformangofzco.presentation.RegistrationFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    fun injectAuthorizationFragment(authorizationFragment: AuthorizationFragment)
    fun injectRegistrationFragment(registrationFragment: RegistrationFragment)
}