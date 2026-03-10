package com.on.dialog.network.dto.comment

import com.on.dialog.core.common.extension.toIsoLocalDateTime
import com.on.dialog.model.common.ProfileImage
import com.on.dialog.model.discussion.comment.DiscussionComment
import com.on.dialog.model.discussion.content.Author
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchDiscussionCommentsResponse(
    @SerialName("discussionComments")
    val discussionComments: List<DiscussionCommentDto>,
) {
    fun toDomain(): List<DiscussionComment> = discussionComments.map(DiscussionCommentDto::toDomain)

    @Serializable
    data class DiscussionCommentDto(
        @SerialName("discussionCommentId")
        val discussionCommentId: Long,
        @SerialName("content")
        val content: String,
        @SerialName("author")
        val author: AuthorDto,
        @SerialName("childComments")
        val childComments: List<DiscussionCommentDto>,
        @SerialName("createdAt")
        val createdAt: String,
        @SerialName("modifiedAt")
        val modifiedAt: String,
    ) {
        fun toDomain(): DiscussionComment = DiscussionComment(
            id = discussionCommentId,
            content = content,
            author = author.toDomain(),
            childComments = childComments.map(DiscussionCommentDto::toDomain),
            createdAt = createdAt.toIsoLocalDateTime(),
            modifiedAt = modifiedAt.toIsoLocalDateTime(),
        )
    }

    @Serializable
    data class AuthorDto(
        @SerialName("authorId")
        val authorId: Long,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("profileImage")
        val profileImage: ProfileImageDto,
    ) {
        fun toDomain(): Author = Author(
            id = authorId,
            nickname = nickname,
            profileImage = profileImage.toDomain(),
        )
    }

    @Serializable
    data class ProfileImageDto(
        @SerialName("customImageUri")
        val customImageUri: String? = null,
        @SerialName("basicImageUri")
        val basicImageUri: String,
    ) {
        fun toDomain(): ProfileImage = ProfileImage(
            customImageUri = customImageUri,
            basicImageUri = basicImageUri,
        )
    }
}
