package com.on.dialog.feature.login.impl.di

sealed interface AuthQualifier {
    data object Apple : AuthQualifier
}
