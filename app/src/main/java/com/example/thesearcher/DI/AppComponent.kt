package com.example.thesearcher.DI

import android.app.Application
import android.content.Context
import com.example.thesearcher.MainActivity
import com.example.thesearcher.view.ViewPagerActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

//    fun inject(mainActivity: MainActivity)
    fun inject(target: MainActivity)
    fun inject(target: ViewPagerActivity)
//    fun inject(target: View)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun create(): AppComponent
    }
}


