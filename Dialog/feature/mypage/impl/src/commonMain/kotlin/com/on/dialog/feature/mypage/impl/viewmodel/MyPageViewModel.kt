package com.on.dialog.feature.mypage.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.domain.repository.SessionRepository
import com.on.dialog.domain.repository.UserRepository
import com.on.dialog.domain.usecase.session.CheckLoginStatusUseCase
import com.on.dialog.feature.mypage.impl.model.TrackUiModel.Companion.toUiModel
import com.on.dialog.feature.mypage.impl.model.UserInfoUiModel.Companion.toUiModel
import com.on.dialog.model.common.ProfileImage
import com.on.dialog.model.common.Track
import com.on.dialog.model.user.UserInfo
import com.on.dialog.ui.viewmodel.BaseViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val checkLoginStatusUseCase: CheckLoginStatusUseCase,
) : BaseViewModel<MyPageIntent, MyPageState, MyPageEffect>(initialState = MyPageState()) {
    init {
        viewModelScope.launch {
            checkLoginStatusUseCase()
            observeLoginState()
        }
    }

    override fun onIntent(intent: MyPageIntent) {
        when (intent) {
            MyPageIntent.Logout -> logout()

            is MyPageIntent.EditProfile -> updateMyProfile(
                nickname = intent.nickname,
                track = intent.track,
            )

            is MyPageIntent.EditProfileImage -> updateProfileImage(uri = intent.uri)

            MyPageIntent.DeleteAccount -> deleteAccount()
        }
    }

    private fun observeLoginState() {
        viewModelScope.launch {
            sessionRepository.isLoggedIn.collect { isLoggedIn ->
                handleLoginStatusChanged(isLoggedIn)
            }
        }
    }

    private fun handleLoginStatusChanged(isLoggedIn: Boolean?) {
        when (isLoggedIn) {
            true -> {
                loadMyPage()
                loadMyProfileImage()
                updateState { copy(isLoggedIn = true) }
            }

            false -> {
                updateState { MyPageState(isLoggedIn = false, isLoading = false) }
            }

            null -> {
                Unit
            }
        }
    }

    private fun loadMyPage() {
        viewModelScope
            .launch {
                userRepository
                    .getMyUserInfo()
                    .onSuccess(::handleLoadMyPageSuccess)
                    .onFailure { handleLoadMyPageFailure(it) }
            }.invokeOnCompletion {
                updateState { copy(isLoading = false) }
            }
    }

    private fun handleLoadMyPageSuccess(userInfo: UserInfo) = with(userInfo.toUiModel()) {
        updateState {
            copy(
                isLoggedIn = true,
                isLoading = false,
                userInfo = userInfo.toUiModel(),
            )
        }
    }

    private suspend fun handleLoadMyPageFailure(throwable: Throwable) {
        if (throwable is NetworkError.Unauthorized) {
            checkLoginStatusUseCase()
            emitEffect(
                MyPageEffect.ShowSnackbar(
                    message = "로그인 후 이용할 수 있습니다.",
                    state = SnackbarState.NEGATIVE,
                ),
            )
        } else {
            emitEffect(
                MyPageEffect.ShowSnackbar(
                    message = "내 정보를 불러오는데 실패했습니다.",
                    state = SnackbarState.NEGATIVE,
                ),
            )
        }
    }

    private fun loadMyProfileImage() {
        viewModelScope
            .launch {
                userRepository
                    .getMyProfileImage()
                    .onSuccess(::handleLoadMyProfileImageSuccess)
                    .onFailure { handleLoadMyProfileImageFailure(it) }
            }.invokeOnCompletion {
                updateState { copy(isLoading = false) }
            }
    }

    private fun handleLoadMyProfileImageSuccess(profileImage: ProfileImage) {
        updateState {
            copy(
                isLoggedIn = true,
                isLoading = false,
                imageUrl = profileImage.customImageUri ?: profileImage.basicImageUri,
            )
        }
    }

    private suspend fun handleLoadMyProfileImageFailure(throwable: Throwable) {
        if (throwable is NetworkError.Unauthorized) {
            checkLoginStatusUseCase()
            emitEffect(
                MyPageEffect.ShowSnackbar(
                    message = "로그인 후 이용할 수 있습니다.",
                    state = SnackbarState.NEGATIVE,
                ),
            )
        } else {
            Napier.d("내 프로필 이미지를 불러오는데 실패했습니다.")
        }
    }

    private fun updateMyProfile(nickname: String, track: Track) {
        viewModelScope.launch {
            userRepository
                .updateMyProfile(nickname = nickname, track = track)
                .onSuccess { handleUpdateMyProfileSuccess(nickname = nickname, track = track) }
                .onFailure { handleUpdateMyProfileFailure() }
        }
    }

    private fun handleUpdateMyProfileSuccess(nickname: String, track: Track) {
        updateState {
            copy(
                userInfo = userInfo.copy(
                    nickname = nickname,
                    track = track.toUiModel(),
                ),
            )
        }
        emitEffect(
            MyPageEffect.ShowSnackbar(
                message = "프로필이 수정되었습니다.",
                state = SnackbarState.POSITIVE,
            ),
        )
    }

    private fun handleUpdateMyProfileFailure() {
        emitEffect(
            MyPageEffect.ShowSnackbar(
                message = "프로필 수정에 실패했습니다.",
                state = SnackbarState.NEGATIVE,
            ),
        )
    }

    private fun updateProfileImage(uri: String) {
        viewModelScope.launch {
            userRepository
                .updateMyProfileImage(uri = uri)
                .onSuccess(::handleUpdateProfileImageSuccess)
                .onFailure { handleUpdateProfileImageFailure() }
        }
    }

    private fun handleUpdateProfileImageSuccess(image: ProfileImage) {
        updateState {
            copy(imageUrl = image.customImageUri ?: image.basicImageUri)
        }
        emitEffect(
            MyPageEffect.ShowSnackbar(
                message = "프로필 이미지가 수정되었습니다.",
                state = SnackbarState.POSITIVE,
            ),
        )
    }

    private fun handleUpdateProfileImageFailure() {
        emitEffect(
            MyPageEffect.ShowSnackbar(
                message = "프로필 이미지 업로드를 실패했습니다.",
                state = SnackbarState.NEGATIVE,
            ),
        )
    }

    private fun logout() {
        viewModelScope.launch {
            authRepository
                .logout()
                .onSuccess {
                    updateState { MyPageState() }
                    clearUserId()
                    clearSession()
                    checkLoginStatusUseCase()
                }.onFailure { result: Throwable ->
                    emitEffect(
                        MyPageEffect.ShowSnackbar(
                            message = result.message ?: "로그아웃에 실패했습니다.",
                            state = SnackbarState.NEGATIVE,
                        ),
                    )
                }
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch {
            userRepository
                .deleteAccount()
                .onSuccess {
                    updateState { MyPageState(isLoading = false) }
                    clearUserId()
                    clearSession()
                    emitEffect(
                        MyPageEffect.ShowSnackbar(
                            message = "회원 탈퇴에 성공했습니다.",
                            state = SnackbarState.POSITIVE,
                        ),
                    )
                }.onFailure {
                    emitEffect(
                        MyPageEffect.ShowSnackbar(
                            message = "회원 탈퇴에 실패했습니다.",
                            state = SnackbarState.NEGATIVE,
                        ),
                    )
                }
        }
    }

    private suspend fun clearUserId() {
        sessionRepository
            .clearUserId()
            .onFailure { Napier.e(message = it.message.orEmpty(), throwable = it) }
    }

    private suspend fun clearSession() {
        sessionRepository
            .clearSession()
            .onFailure { Napier.e(message = it.message.orEmpty(), throwable = it) }
    }
}
