package com.on.dialog.di

import com.on.dialog.BuildKonfig
import io.sentry.kotlin.multiplatform.Sentry

fun initializeSentry() {
    if (BuildKonfig.IS_DEBUG) return

    Sentry.init { options ->
        options.dsn = BuildKonfig.SENTRY_DSN
    }
}
