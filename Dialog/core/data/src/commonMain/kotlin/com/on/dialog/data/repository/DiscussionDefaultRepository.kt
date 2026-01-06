package com.on.dialog.data.repository

import com.on.dialog.domain.repository.DiscussionRepository
import com.on.model.discussion.criteria.DiscussionCriteria
import com.on.model.discussion.cursorpage.DiscussionCatalogCursorPage
import com.on.model.discussion.detail.DiscussionDetail
import com.on.model.discussion.draft.OfflineDiscussionDraft
import com.on.model.discussion.draft.OnlineDiscussionDraft
import com.on.model.discussion.summary.DiscussionSummary
import com.on.network.datasource.DiscussionDatasource
import com.on.network.dto.discussioncreate.CreateOfflineDiscussionRequest.Companion.toCreationRequest
import com.on.network.dto.discussioncreate.CreateOnlineDiscussionRequest.Companion.toCreateRequest
import com.on.network.dto.discussionedit.OfflineDiscussionEditRequest.Companion.toEditRequest
import com.on.network.dto.discussionedit.OnlineDiscussionEditRequest.Companion.toEditRequest
import com.on.network.dto.discussionlookup.DiscussionQuery.Companion.toQuery
import com.on.network.dto.discussionsummary.DiscussionSummaryRequest.Companion.toRequest

internal class DiscussionDefaultRepository(
    private val discussionDatasource: DiscussionDatasource,
) : DiscussionRepository {
    override suspend fun getDiscussionDetail(id: Long): Result<DiscussionDetail> =
        discussionDatasource.getDiscussionDetail(id).mapCatching { it.toDomain() }

    override suspend fun getDiscussions(
        discussionCriteria: DiscussionCriteria,
        cursor: String,
        size: Int,
    ): Result<DiscussionCatalogCursorPage> =
        discussionDatasource.getDiscussions(discussionCriteria.toQuery(cursor, size)).mapCatching { it.toDomain() }

    override suspend fun getMyDiscussions(): Result<DiscussionCatalogCursorPage> =
        discussionDatasource.getMyDiscussions().mapCatching { it.toDomain() }

    override suspend fun searchDiscussions(
        searchBy: Int,
        keyword: String,
        discussionCriteria: DiscussionCriteria,
        cursor: String,
        size: Int,
    ): Result<DiscussionCatalogCursorPage> =
        discussionDatasource.searchDiscussions(searchBy, keyword, discussionCriteria.toQuery(cursor, size)).mapCatching { it.toDomain() }

    override suspend fun createOfflineDiscussion(request: OfflineDiscussionDraft): Result<Long> =
        discussionDatasource.createOfflineDiscussion(request.toCreationRequest()).mapCatching { it.discussionId }

    override suspend fun createOnlineDiscussion(request: OnlineDiscussionDraft): Result<Long> =
        discussionDatasource.createOnlineDiscussion(request.toCreateRequest()).mapCatching { it.discussionId }

    override suspend fun createDiscussionSummary(discussionId: Long): Result<DiscussionSummary> =
        discussionDatasource.createDiscussionSummary(discussionId.toRequest()).mapCatching { it.toDomain() }

    override suspend fun updateOfflineDiscussion(
        id: Long,
        offlineDiscussionDraft: OfflineDiscussionDraft,
    ): Result<Unit> =
        discussionDatasource.updateOfflineDiscussion(id, offlineDiscussionDraft.toEditRequest())

    override suspend fun updateOnlineDiscussion(
        id: Long,
        onlineDiscussionDraft: OnlineDiscussionDraft,
    ): Result<Unit> =
        discussionDatasource.updateOnlineDiscussion(id, onlineDiscussionDraft.toEditRequest())

    override suspend fun deleteDiscussion(id: Long): Result<Unit> = discussionDatasource.deleteDiscussion(id)
}
