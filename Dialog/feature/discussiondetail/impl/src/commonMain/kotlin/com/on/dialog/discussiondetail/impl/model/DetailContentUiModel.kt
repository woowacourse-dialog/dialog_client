package com.on.dialog.discussiondetail.impl.model

import androidx.compose.runtime.Immutable
import com.on.dialog.core.common.extension.formatToString
import com.on.dialog.discussiondetail.impl.model.DetailContentUiModel.AuthorUiModel.Companion.toUiModel
import com.on.dialog.discussiondetail.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.model.discussion.content.Author
import com.on.dialog.model.discussion.content.DetailContent

@Immutable
data class DetailContentUiModel(
    val id: Long,
    val title: String,
    val author: AuthorUiModel,
    val category: TrackUiModel,
    val content: String,
    val createdAt: String,
    val likeCount: Int,
    val modifiedAt: String,
) {
    @Immutable
    data class AuthorUiModel(
        val id: Long,
        val nickname: String,
        val imageUrl: String,
    ) {
        companion object {
            fun Author.toUiModel(): AuthorUiModel = AuthorUiModel(
                id = id,
                nickname = nickname,
                imageUrl = profileImage?.customImageUri ?: profileImage?.basicImageUri.orEmpty(),
            )
        }
    }

    companion object {
        fun DetailContent.toUiModel(): DetailContentUiModel = DetailContentUiModel(
            id = id,
            title = title,
            author = author.toUiModel(),
            category = category.toUiModel(),
            content = content,
            createdAt = createdAt.date.formatToString("yyyy년 M월 d일"),
            likeCount = likeCount,
            modifiedAt = modifiedAt.date.formatToString("yyyy년 M월 d일"),
        )
    }
}
