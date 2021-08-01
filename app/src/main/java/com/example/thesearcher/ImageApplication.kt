package com.example.thesearcher

import android.app.Application
import android.content.Context
import com.example.thesearcher.DI.AppComponent
import com.example.thesearcher.DI.DaggerAppComponent

class ImageApplication : Application() {

    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent)

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.builder()
            .application(this)
            .create()
    }
}

val Context.appComponent: AppComponent // TODO :: А надо ли ???
    get() = when (this) {
        is ImageApplication -> appComponent
        else -> (applicationContext as ImageApplication).appComponent
    }