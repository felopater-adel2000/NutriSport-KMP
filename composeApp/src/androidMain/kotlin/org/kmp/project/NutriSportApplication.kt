package org.kmp.project

import android.app.Application
import com.nutrisport.di.initializeKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext


class NutriSportApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        initializeKoin {
            androidContext(this@NutriSportApplication)
        }

    }
}