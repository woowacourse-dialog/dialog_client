package com.on.dialog.ui.component.markdown.style

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.on.dialog.designsystem.icon.DialogIcons

sealed interface MarkdownStyle {
    val icon: ImageVector

    fun transform(content: TextFieldValue): TextFieldValue

    sealed class Inline(
        protected val prefix: String,
        protected val suffix: String,
        override val icon: ImageVector,
    ) : MarkdownStyle {
        override fun transform(content: TextFieldValue): TextFieldValue {
            val text = content.text
            val selection = content.selection

            val start = minOf(selection.start, selection.end)
            val end = maxOf(selection.start, selection.end)

            if (start == end) {
                val newText = text.replaceRange(start, start, prefix + suffix)
                return content.copy(
                    text = newText,
                    selection = TextRange(start + prefix.length),
                )
            }

            val selectedText = text.substring(start, end)

            val hasStyle =
                selectedText.startsWith(prefix) &&
                    selectedText.endsWith(suffix) &&
                    selectedText.length >= prefix.length + suffix.length

            return if (hasStyle) {
                val unwrapped = selectedText
                    .removePrefix(prefix)
                    .removeSuffix(suffix)

                content.copy(
                    text = text.replaceRange(start, end, unwrapped),
                    selection = TextRange(start, start + unwrapped.length),
                )
            } else {
                content.copy(
                    text = text.replaceRange(
                        start,
                        end,
                        "$prefix$selectedText$suffix",
                    ),
                    selection = TextRange(
                        start,
                        start + selectedText.length + prefix.length + suffix.length,
                    ),
                )
            }
        }
    }

    sealed class Block(
        protected val linePrefix: String,
        override val icon: ImageVector,
    ) : MarkdownStyle {
        override fun transform(content: TextFieldValue): TextFieldValue {
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
                        startLineStart + linePrefix.length,
                    )
                } else {
                    text.replaceRange(
                        startLineStart,
                        startLineStart,
                        linePrefix,
                    )
                }

            val offset = if (hasPrefix) -linePrefix.length else linePrefix.length

            return content.copy(
                text = newText,
                selection = TextRange(
                    (selection.start + offset).coerceAtLeast(0),
                    (selection.end + offset).coerceAtLeast(0),
                ),
            )
        }
    }

    object Bold : Inline("**", "**", DialogIcons.bold)

    object Italic : Inline("*", "*", DialogIcons.italic)

    object Code : Inline("`", "`", DialogIcons.code)

    object Quote : Block("> ", DialogIcons.quote)

    object Bullet : Block("- ", DialogIcons.bullet)

    object Number : MarkdownStyle {
        override val icon: ImageVector = DialogIcons.number

        private val numberPattern = Regex("^(\\d+)\\. ")

        override fun transform(content: TextFieldValue): TextFieldValue {
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
                    startLineStart + match.value.length,
                )
            } else {
                text.replaceRange(
                    startLineStart,
                    startLineStart,
                    "1. ",
                )
            }

            val offset = if (match != null) -match.value.length else 3

            return content.copy(
                text = newText,
                selection = TextRange(
                    (selection.start + offset).coerceAtLeast(0),
                    (selection.end + offset).coerceAtLeast(0),
                ),
            )
        }

        fun handleNewLine(content: TextFieldValue): TextFieldValue? {
            val text = content.text
            val cursorPosition = content.selection.start

            val prevLineStart = text.lastIndexOf('\n', cursorPosition - 2).let {
                if (it == -1) 0 else it + 1
            }

            val prevLineEnd = cursorPosition - 1
            if (prevLineStart >= prevLineEnd) return null

            val prevLine = text.substring(prevLineStart, prevLineEnd)

            val match = numberPattern.find(prevLine) ?: return null

            val currentNumber = match.groupValues[1].toInt()

            if (prevLine.trim() == match.value.trim()) {
                val newText = text.removeRange(prevLineStart, cursorPosition)
                return content.copy(
                    text = newText,
                    selection = TextRange(prevLineStart),
                )
            }

            val nextNumber = currentNumber + 1
            val nextPrefix = "$nextNumber. "

            val newText = text.replaceRange(
                cursorPosition,
                cursorPosition,
                nextPrefix,
            )

            return content.copy(
                text = newText,
                selection = TextRange(cursorPosition + nextPrefix.length),
            )
        }
    }

    object Link : Inline("[", "](url)", DialogIcons.link) {
        override fun transform(content: TextFieldValue): TextFieldValue {
            val text = content.text
            val selection = content.selection
            val start = minOf(selection.start, selection.end)
            val end = maxOf(selection.start, selection.end)

            if (start == end) {
                val newText = text.replaceRange(start, start, "[](url)")
                return content.copy(
                    text = newText,
                    selection = TextRange(start + 3, start + 6),
                )
            } else {
                val selectedText = text.substring(start, end)
                val newText = text.replaceRange(start, end, "[$selectedText](url)")
                return content.copy(
                    text = newText,
                    selection = TextRange(
                        start + selectedText.length + 3,
                        start + selectedText.length + 6,
                    ),
                )
            }
        }
    }

    object CodeBlock : MarkdownStyle {
        override val icon: ImageVector = DialogIcons.codeBlock

        override fun transform(content: TextFieldValue): TextFieldValue {
            val text = content.text
            val selection = content.selection

            val start = minOf(selection.start, selection.end)
            val end = maxOf(selection.start, selection.end)

            val selectedText =
                if (start == end) "" else text.substring(start, end)

            val isCodeBlock = selectedText.startsWith("```\n") &&
                selectedText.endsWith("\n```") &&
                selectedText.length >= 8
            return if (isCodeBlock) {
                val unwrapped = selectedText.removePrefix("```\n").removeSuffix("\n```")
                content.copy(
                    text = text.replaceRange(start, end, unwrapped),
                    selection = TextRange(start, start + unwrapped.length),
                )
            } else {
                val block = "```\n$selectedText\n```"
                content.copy(
                    text = text.replaceRange(start, end, block),
                    selection = TextRange(start + 4),
                )
            }
        }
    }

    companion object {
        val markdownStyles: List<MarkdownStyle> =
            listOf(Bold, Italic, Code, Quote, Bullet, Number, Link, CodeBlock)
    }
}
