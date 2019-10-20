package com.jkirtyan.exchangerate.di

import com.jkirtyan.exchangerate.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetModule::class)])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}