package com.on.dialog

import android.app.Application
import com.on.dialog.di.initKoin
import org.koin.android.ext.koin.androidContext

class DialogApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@DialogApplication)
        }
    }
}
