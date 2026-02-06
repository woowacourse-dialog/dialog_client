package com.on.dialog.feature.signup.impl

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.on.dialog.designsystem.component.DialogDropdownMenu
import com.on.dialog.designsystem.theme.DialogTheme
import com.on.dialog.feature.signup.impl.mapper.toFullNameRes
import com.on.dialog.model.common.Track
import dialog.feature.signup.impl.generated.resources.Res
import dialog.feature.signup.impl.generated.resources.track
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    val tracks: ImmutableList<String> =
        Track.entries
            .filter { it != Track.COMMON }
            .map { stringResource(it.toFullNameRes()) }
            .toImmutableList()

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    DialogDropdownMenu(
        options = tracks,
        onSelectedIndexChange = { selectedIndex = it },
        label = stringResource(Res.string.track),
        selectedIndex = selectedIndex,
    )
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    DialogTheme {
        Surface {
            SignUpScreen()
        }
    }
}
