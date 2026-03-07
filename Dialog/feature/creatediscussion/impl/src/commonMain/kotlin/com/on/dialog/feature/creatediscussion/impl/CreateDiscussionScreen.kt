package com.on.dialog.feature.creatediscussion.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.on.dialog.designsystem.component.DialogDropdownMenu
import com.on.dialog.designsystem.component.DialogIconButton
import com.on.dialog.designsystem.component.DialogTextField
import com.on.dialog.designsystem.component.DialogToggle
import com.on.dialog.designsystem.component.DialogTopAppBar
import com.on.dialog.designsystem.icon.DialogIcons
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionIntent
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionState
import com.on.dialog.feature.creatediscussion.impl.viewmodel.CreateDiscussionViewModel
import kotlinx.collections.immutable.persistentListOf
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
        onIntent = viewModel::onIntent,
        modifier = modifier,
    )
}

@Composable
private fun CreateDiscussionScreen(
    uiState: CreateDiscussionState,
    onBackClick: () -> Unit,
    onIntent: (CreateDiscussionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    var isMeetupEnabled by remember { mutableStateOf(true) }

    Column(modifier = modifier.fillMaxSize()) {
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
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            TitleField(
                title = uiState.title,
                onTitleChange = { onIntent(CreateDiscussionIntent.OnTitleChange(it)) },
            )

            DialogDropdownMenu(
                options = persistentListOf("안드로이드", "백엔드", "프론트엔드"),
                onSelectedIndexChange = { selectedIndex = it },
                label = "트랙",
                placeholder = "트랙을 선택해주세요",
            )

            Spacer(modifier = Modifier.height(20.dp))

            DialogToggle(
                checked = isMeetupEnabled,
                onCheckedChange = { isMeetupEnabled = it },
                label = "만나서 토론하기",
                modifier = Modifier.padding(start = DialogTheme.spacing.small)
            )

            Spacer(modifier = Modifier.height(20.dp))

            DialogDropdownMenu(
                options = persistentListOf("1일 후", "2일 후", "3일 후"),
                onSelectedIndexChange = { selectedIndex = it },
                label = "토론 종료 날짜",
                placeholder = "날짜를 선택해주세요",
            )
        }
    }
}

@Composable
private fun TitleField(
    title: String,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box {
            DialogTextField(
                value = title,
                onValueChange = { if (it.length <= 50) onTitleChange(it) },
                label = "제목",
                modifier = Modifier.fillMaxWidth(),
                placeholder = "제목에 핵심 내용을 요약합니다.",
                singleLine = true,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = "${title.length} / 50",
                color = DialogTheme.colorScheme.primary,
                fontSize = 12.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateDiscussionScreenPreview() {
    DialogTheme {
        CreateDiscussionScreen(
            uiState = CreateDiscussionState(),
            onBackClick = {},
            onIntent = {},
        )
    }
}
