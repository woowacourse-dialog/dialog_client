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
    object Number : MarkdownStyle() {
        private val numberPattern = Regex("^(\\d+)\\. ")

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

            val lineEnd = text.indexOf('\n', startLineStart).let {
                if (it == -1) text.length else it
            }
            val currentLine = text.substring(startLineStart, lineEnd)

            val match = numberPattern.find(currentLine)

            val newText = if (match != null) {
                text.removeRange(
                    startLineStart,
                    startLineStart + match.value.length
                )
            } else {
                text.replaceRange(
                    startLineStart,
                    startLineStart,
                    "1. "
                )
            }

            val offset = if (match != null) -match.value.length else 3

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

        fun handleNewLine(
            content: TextFieldValue,
            onContentChanged: (TextFieldValue) -> Unit,
        ): Boolean {
            val text = content.text
            val cursorPosition = content.selection.start

            val prevLineStart = text.lastIndexOf('\n', cursorPosition - 2).let {
                if (it == -1) 0 else it + 1
            }

            val prevLineEnd = cursorPosition - 1
            if (prevLineStart >= prevLineEnd) return false

            val prevLine = text.substring(prevLineStart, prevLineEnd)

            val match = numberPattern.find(prevLine) ?: return false

            val currentNumber = match.groupValues[1].toInt()

            if (prevLine.trim() == match.value.trim()) {
                val newText = text.removeRange(prevLineStart, cursorPosition)
                onContentChanged(
                    content.copy(
                        text = newText,
                        selection = TextRange(prevLineStart)
                    )
                )
                return true
            }

            val nextNumber = currentNumber + 1
            val nextPrefix = "$nextNumber. "

            val newText = text.replaceRange(
                cursorPosition,
                cursorPosition,
                nextPrefix
            )

            onContentChanged(
                content.copy(
                    text = newText,
                    selection = TextRange(cursorPosition + nextPrefix.length)
                )
            )

            return true
        }
    }

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
