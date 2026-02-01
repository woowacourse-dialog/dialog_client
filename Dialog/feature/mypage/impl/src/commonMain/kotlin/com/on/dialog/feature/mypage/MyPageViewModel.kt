package com.on.dialog.feature.mypage

import androidx.lifecycle.viewModelScope
import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.domain.repository.UserRepository
import com.on.dialog.feature.mypage.model.UserInfoUiModel.Companion.toDomain
import com.on.dialog.model.common.ProfileImage
import com.on.dialog.model.user.UserInfo
import com.on.dialog.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : BaseViewModel<MyPageIntent, MyPageState, MyPageEffect>(initialState = MyPageState()) {
    override fun onIntent(intent: MyPageIntent) {
        when (intent) {
            MyPageIntent.CheckLoginStatus -> getLoginStatus()
            MyPageIntent.Logout -> logout()
        }
    }

    private fun getLoginStatus() {
        if (currentState.isLoggedIn) return

        viewModelScope
            .launch {
                authRepository
                    .getLoginStatus()
                    .onSuccess(::handleGetLoginStatus)
            }.invokeOnCompletion {
                updateState { copy(isLoading = false) }
            }
    }

    private fun handleGetLoginStatus(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            loadMyPage()
            loadMyProfileImage()
        }
        updateState { copy(isLoggedIn = isLoggedIn) }
    }

    private fun loadMyPage() {
        viewModelScope
            .launch {
                userRepository
                    .getMyUserInfo()
                    .onSuccess(::handleLoadMyPageSuccess)
                    .onFailure(::handleLoadMyPageFailure)
            }.invokeOnCompletion {
                updateState { copy(isLoading = false) }
            }
    }

    private fun handleLoadMyPageSuccess(userInfo: UserInfo) = with(userInfo.toDomain()) {
        updateState {
            copy(
                isLoggedIn = true,
                track = track,
                nickname = nickname,
                githubId = githubId,
                isNotificationEnable = isNotificationEnabled,
            )
        }
    }

    private fun handleLoadMyPageFailure(throwable: Throwable) {
        if (throwable is NetworkError.Unauthorized) {
            updateState { copy(isLoggedIn = false) }
            emitEffect(
                MyPageEffect.ShowError(message = throwable.message ?: "로그인 후 이용할 수 있습니다."),
            )
        } else {
            emitEffect(MyPageEffect.ShowError(message = "내 정보를 불러오는데 실패했습니다."))
        }
    }

    private fun loadMyProfileImage() {
        viewModelScope
            .launch {
                userRepository
                    .getMyProfileImage()
                    .onSuccess(::handleLoadMyProfileImageSuccess)
                    .onFailure(::handleLoadMyProfileImageFailure)
            }.invokeOnCompletion {
                updateState { copy(isLoading = false) }
            }
    }

    private fun handleLoadMyProfileImageSuccess(profileImage: ProfileImage) {
        updateState {
            copy(
                isLoggedIn = true,
                isLoading = false,
                imageUrl = profileImage.customImageUri ?: profileImage.basicImageUri ?: "",
            )
        }
    }

    private fun handleLoadMyProfileImageFailure(throwable: Throwable) {
        if (throwable is NetworkError.Unauthorized) {
            updateState { copy(isLoggedIn = false) }
            emitEffect(
                MyPageEffect.ShowError(message = throwable.message ?: "로그인 후 이용할 수 있습니다."),
            )
        } else {
            emitEffect(MyPageEffect.ShowError(message = "내 프로필 이미지를 불러오는데 실패했습니다."))
        }
    }

    private fun logout() {
        viewModelScope.launch {
            authRepository
                .logout()
                .onSuccess { updateState { MyPageState() } }
                .onFailure { result: Throwable ->
                    emitEffect(
                        MyPageEffect.ShowError(message = result.message ?: "로그아웃에 실패했습니다."),
                    )
                }
        }
    }
}
