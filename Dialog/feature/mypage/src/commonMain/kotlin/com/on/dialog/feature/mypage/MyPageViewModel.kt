package com.on.dialog.feature.mypage

import androidx.lifecycle.viewModelScope
import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.domain.repository.UserRepository
import com.on.dialog.ui.viewmodel.BaseViewModel
import com.on.dialog.ui.viewmodel.UiEffect
import com.on.dialog.ui.viewmodel.UiIntent
import com.on.dialog.ui.viewmodel.UiState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

sealed interface MyPageUiIntent : UiIntent {
    data object LoadMyPage : MyPageUiIntent

    data object Logout : MyPageUiIntent
}

sealed interface MyPageUiEffect : UiEffect {
    data class ShowError(
        val message: String,
    ) : MyPageUiEffect
}

data class MyPageUiState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true,
    val imageUrl: String = "",
    val nickname: String = "",
    val track: String = "",
    val githubId: String = "",
    val isNotificationEnable: Boolean = false,
) : UiState

class MyPageViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : BaseViewModel<MyPageUiIntent, MyPageUiState, MyPageUiEffect>(MyPageUiState()) {
    override fun onIntent(intent: MyPageUiIntent) {
        when (intent) {
            MyPageUiIntent.LoadMyPage -> {
                loadMyPage()
                loadMyProfileImage()
            }

            MyPageUiIntent.Logout -> {
                logout()
            }
        }
    }

    private fun loadMyPage() {
        viewModelScope.launch {
            userRepository
                .getMyUserInfo()
                .onSuccess { userInfo ->
                    updateState {
                        copy(
                            isLoggedIn = true,
                            isLoading = false,
                            track = userInfo.track.initial,
                            nickname = userInfo.nickname,
                            githubId = userInfo.githubId,
                            isNotificationEnable = userInfo.isNotificationEnabled,
                        )
                    }
                }.onFailure { result ->
                    if (result is NetworkError.Unauthorized) {
                        updateState {
                            copy(isLoading = false, isLoggedIn = false)
                        }
                        emitEffect(MyPageUiEffect.ShowError(result.message ?: "로그인 후 이용할 수 있습니다."))
                    } else {
                        emitEffect(MyPageUiEffect.ShowError("내 정보를 불러오는데 실패했습니다."))
                    }
                }
        }
    }

    private fun loadMyProfileImage() {
        viewModelScope.launch {
            userRepository
                .getMyProfileImage()
                .onSuccess { profileImage ->
                    updateState {
                        copy(
                            isLoggedIn = true,
                            isLoading = false,
                            imageUrl = profileImage.customImageUri ?: profileImage.basicImageUri
                                ?: "",
                        )
                    }
                }.onFailure { result: Throwable ->
                    if (result is NetworkError.Unauthorized) {
                        updateState {
                            copy(isLoading = false, isLoggedIn = false)
                        }
                        emitEffect(MyPageUiEffect.ShowError(result.message ?: "로그인 후 이용할 수 있습니다."))
                    } else {
                        emitEffect(MyPageUiEffect.ShowError("내 프로필 이미지를 불러오는데 실패했습니다."))
                    }
                }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            authRepository
                .logout()
                .onSuccess {
                    updateState { MyPageUiState() }
                    Napier.d("로그아웃 성공")
                }.onFailure { result: Throwable ->
                    Napier.w("로그아웃 실패")
                    emitEffect(MyPageUiEffect.ShowError(result.message ?: "로그아웃에 실패했습니다."))
                }
        }
    }
}
