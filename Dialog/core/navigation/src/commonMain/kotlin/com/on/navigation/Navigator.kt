package com.on.navigation

import androidx.navigation3.runtime.NavKey
import io.github.aakira.napier.Napier

class Navigator(
    val state: NavigationState,
) {
    fun navigate(key: NavKey) {
        when (key) {
            state.currentTopLevelKey -> clearSubStack()
            in state.topLevelKeys -> goToTopLevel(key)
            else -> goToKey(key)
        }
        Napier.e(
            tag = "Navigator",
            message =
                """
            
            ------------------------ Navigator --------------------------------
            [navigate to $key]
            currentTopLevelKey: ${state.currentTopLevelKey}
            currentKey: ${state.currentKey}

            topLevelStack: ${state.topLevelStack}
            subStacks: ${state.subStacks}
            currentSubStack: ${state.currentSubStack.size}
            ------------------------------------------------------------------------
            """.trimIndent(),
        )
    }

    fun goBack() {
        when (state.currentKey) {
            state.startKey -> Unit
            state.currentTopLevelKey -> state.topLevelStack.removeLastOrNull()
            else -> state.currentSubStack.removeLastOrNull()
        }
        Napier.e(
            tag = "Navigator",
            message =
                """
            
            ------------------------ Navigator --------------------------------
            [goback]
            currentTopLevelKey: ${state.currentTopLevelKey}
            currentKey: ${state.currentKey}

            topLevelStack: ${state.topLevelStack}
            subStacks: ${state.subStacks}
            currentSubStack: ${state.currentSubStack.size}
            ------------------------------------------------------------------------
            """.trimIndent(),
        )
    }

    private fun goToKey(key: NavKey) {
        state.currentSubStack.apply {
            remove(key)
            add(key)
        }
    }

    private fun goToTopLevel(key: NavKey) {
        state.topLevelStack.apply {
            if (key == state.startKey) clear() else remove(key)
            add(key)
        }
    }

    private fun clearSubStack() {
        state.currentSubStack.run {
            if (size > 1) subList(1, size).clear()
        }
    }
}
