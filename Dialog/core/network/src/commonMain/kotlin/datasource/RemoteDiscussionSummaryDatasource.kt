package datasource

import com.on.dialog.data.datasource.DiscussionSummaryDatasource
import com.on.dialog.data.dto.request.DiscussionSummaryRequest
import com.on.dialog.data.dto.response.discussionsummary.DiscussionSummaryResponse

class RemoteDiscussionSummaryDatasource : DiscussionSummaryDatasource {
    override suspend fun createDiscussionSummary(request: DiscussionSummaryRequest): Result<DiscussionSummaryResponse> {
        TODO("Not yet implemented")
    }
}