package com.on.dialog

import android.app.Application
import com.on.dialog.di.initKoin
import com.on.dialog.di.initLogger
import com.on.dialog.di.initializeSentry
import org.koin.android.ext.koin.androidContext

class DialogApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initializeSentry()
        initLogger()
        initKoin {
            androidContext(this@DialogApplication)
        }
    }
}
