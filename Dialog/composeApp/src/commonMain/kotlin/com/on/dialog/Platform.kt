package com.on.dialog

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform