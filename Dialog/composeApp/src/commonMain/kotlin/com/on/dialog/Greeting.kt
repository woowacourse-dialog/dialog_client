package com.on.dialog

import com.on.dialog.core.common.currentPlatform

class Greeting {
    fun greet(): String = "Hello, ${currentPlatform.name}!"
}
