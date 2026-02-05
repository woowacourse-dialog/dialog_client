package com.on.dialog.ui.component.button

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

sealed class MarkdownStyle {

    abstract fun apply(
        content: TextFieldValue,
        onContentChanged: (TextFieldValue) -> Unit,
    )

    sealed class Inline(
        protected val prefix: String,
        protected val suffix: String,
    ) : MarkdownStyle() {

        override fun apply(
            content: TextFieldValue,
            onContentChanged: (TextFieldValue) -> Unit,
        ) {
            val text = content.text
            val selection = content.selection

            val start = minOf(selection.start, selection.end)
            val end = maxOf(selection.start, selection.end)

            if (start == end) {
                val newText = text.replaceRange(start, start, prefix + suffix)
                onContentChanged(
                    content.copy(
                        text = newText,
                        selection = TextRange(start + prefix.length)
                    )
                )
                return
            }

            val selectedText = text.substring(start, end)

            val hasStyle =
                selectedText.startsWith(prefix) &&
                        selectedText.endsWith(suffix) &&
                        selectedText.length >= prefix.length + suffix.length

            if (hasStyle) {
                val unwrapped = selectedText
                    .removePrefix(prefix)
                    .removeSuffix(suffix)

                onContentChanged(
                    content.copy(
                        text = text.replaceRange(start, end, unwrapped),
                        selection = TextRange(start, start + unwrapped.length)
                    )
                )
            } else {
                onContentChanged(
                    content.copy(
                        text = text.replaceRange(
                            start,
                            end,
                            "$prefix$selectedText$suffix"
                        ),
                        selection = TextRange(
                            start,
                            start + selectedText.length + prefix.length + suffix.length
                        )
                    )
                )
            }
        }
    }

    sealed class Block(
        protected val linePrefix: String,
    ) : MarkdownStyle() {

        override fun apply(
            content: TextFieldValue,
            onContentChanged: (TextFieldValue) -> Unit,
        ) {
            val text = content.text
            val selection = content.selection

            val startLineStart =
                text.lastIndexOf('\n', startIndex = selection.start - 1).let {
                    if (it == -1) 0 else it + 1
                }

            val hasPrefix = text.startsWith(linePrefix, startLineStart)

            val newText =
                if (hasPrefix) {
                    text.removeRange(
                        startLineStart,
                        startLineStart + linePrefix.length
                    )
                } else {
                    text.replaceRange(
                        startLineStart,
                        startLineStart,
                        linePrefix
                    )
                }

            val offset = if (hasPrefix) -linePrefix.length else linePrefix.length

            onContentChanged(
                content.copy(
                    text = newText,
                    selection = TextRange(
                        (selection.start + offset).coerceAtLeast(0),
                        (selection.end + offset).coerceAtLeast(0)
                    )
                )
            )
        }
    }

    object Bold : Inline("**", "**")
    object Italic : Inline("*", "*")
    object Code : Inline("`", "`")

    object Quote : Block("> ")
    object Bullet : Block("- ")
    object Number : Block("1. ")

    object Link : Inline("[", "](url)") {
        override fun apply(
            content: TextFieldValue,
            onContentChanged: (TextFieldValue) -> Unit,
        ) {
            val text = content.text
            val selection = content.selection
            val start = minOf(selection.start, selection.end)
            val end = maxOf(selection.start, selection.end)

            if (start == end) {
                val newText = text.replaceRange(start, start, "[](url)")
                onContentChanged(
                    content.copy(
                        text = newText,
                        selection = TextRange(start + 3, start + 6)
                    )
                )
            } else {
                val selectedText = text.substring(start, end)
                val newText = text.replaceRange(start, end, "[$selectedText](url)")
                onContentChanged(
                    content.copy(
                        text = newText,
                        selection = TextRange(
                            start + selectedText.length + 3,
                            start + selectedText.length + 6
                        )
                    )
                )
            }
        }
    }

    object CodeBlock : MarkdownStyle() {
        override fun apply(
            content: TextFieldValue,
            onContentChanged: (TextFieldValue) -> Unit,
        ) {
            val text = content.text
            val selection = content.selection

            val start = minOf(selection.start, selection.end)
            val end = maxOf(selection.start, selection.end)

            val selectedText =
                if (start == end) "" else text.substring(start, end)

            val block = "```\n$selectedText\n```"

            onContentChanged(
                content.copy(
                    text = text.replaceRange(start, end, block),
                    selection = TextRange(start + 4)
                )
            )
        }
    }
}
