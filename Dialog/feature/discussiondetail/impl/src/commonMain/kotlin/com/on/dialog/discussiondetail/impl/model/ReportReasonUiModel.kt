package com.on.dialog.discussiondetail.impl.model

import androidx.compose.runtime.Composable
import dialog.feature.discussiondetail.impl.generated.resources.Res
import dialog.feature.discussiondetail.impl.generated.resources.report_reason_abusive
import dialog.feature.discussiondetail.impl.generated.resources.report_reason_inappropriate
import dialog.feature.discussiondetail.impl.generated.resources.report_reason_other
import dialog.feature.discussiondetail.impl.generated.resources.report_reason_spam
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

internal enum class ReportReasonUiModel(
    private val titleResId: StringResource,
) {
    SPAM(titleResId = Res.string.report_reason_spam),
    INAPPROPRIATE(titleResId = Res.string.report_reason_inappropriate),
    ABUSE(titleResId = Res.string.report_reason_abusive),
    OTHER(titleResId = Res.string.report_reason_other),
    ;

    val title: String
        @Composable get() = stringResource(titleResId)
}
