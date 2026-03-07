package com.on.dialog.feature.creatediscussion.impl

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionState
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CreateDiscussionScreen(
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateDiscussionViewModel = koinViewModel(),
) {
    val uiState: CreateDiscussionState by viewModel.uiState.collectAsStateWithLifecycle()

    CreateDiscussionScreen(
        uiState = uiState,
        onBackClick = goBack,
        modifier = modifier,
    )
}

@Composable
private fun CreateDiscussionScreen(
    uiState: CreateDiscussionState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DialogTopAppBar(
                title = "토론 작성",
                navigationIcon = {
                    DialogIconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = DialogIcons.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                centerAligned = false,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = if (uiState.isSubmitting) "토론 생성 중..." else "CreateDiscussion 기본 화면",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateDiscussionScreenPreview1() {
    CreateDiscussionScreen(
        uiState = CreateDiscussionState(
            isSubmitting = false,
        ),
        onBackClick = {},
    )
}


@Preview(showBackground = true)
@Composable
private fun CreateDiscussionScreenPreview() {
    CreateDiscussionScreen(
        uiState = CreateDiscussionState(
            isSubmitting = true,
        ),
        onBackClick = {},
    )
}
