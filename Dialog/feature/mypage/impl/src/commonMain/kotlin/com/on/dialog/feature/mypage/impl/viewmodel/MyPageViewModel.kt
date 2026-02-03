package com.on.dialog.feature.mypage.impl.viewmodel

import androidx.lifecycle.viewModelScope
import com.on.dialog.core.common.error.NetworkError
import com.on.dialog.designsystem.component.snackbar.SnackbarState
import com.on.dialog.domain.repository.AuthRepository
import com.on.dialog.domain.repository.UserRepository
import com.on.dialog.feature.mypage.impl.mapper.toInitial
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
) : BaseViewModel<MyPageIntent, MyPageState, MyPageEffect>(initialState = MyPageState()) {
    override fun onIntent(intent: MyPageIntent) {
        when (intent) {
            MyPageIntent.CheckLoginStatus -> getLoginStatus()
            MyPageIntent.Logout -> logout()
            is MyPageIntent.EditProfile -> updateProfile(
                nickname = intent.nickname,
                track = intent.track,
            )

            is MyPageIntent.EditProfileImage -> updateProfileImage(uri = intent.uri)
        }
    }

    private fun getLoginStatus() {
        if (currentState.isLoggedIn) return

        viewModelScope
            .launch {
                authRepository
                    .getLoginStatus()
                    .onSuccess { isLoggedIn: Boolean ->
                        if (isLoggedIn) {
                            loadMyPage()
                            loadMyProfileImage()
                            updateState { copy(isLoggedIn = true) }
                        } else {
                            updateState { copy(isLoggedIn = false) }
                        }
                    }
            }.invokeOnCompletion {
                updateState { copy(isLoading = false) }
            }
    }

    private fun loadMyPage() {
        viewModelScope
            .launch {
                userRepository
                    .getMyUserInfo()
                    .onSuccess { userInfo: UserInfo ->
                        updateState {
                            copy(
                                isLoggedIn = true,
                                isLoading = false,
                                userInfo = userInfo.toUiModel(),
                            )
                        }
                    }.onFailure { result ->
                        if (result is NetworkError.Unauthorized) {
                            updateState { copy(isLoggedIn = false) }
                            emitEffect(
                                MyPageEffect.ShowSnackbar(
                                    message = result.message ?: "로그인 후 이용할 수 있습니다.",
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
            }.invokeOnCompletion {
                updateState { copy(isLoading = false) }
            }
    }

    private fun loadMyProfileImage() {
        viewModelScope
            .launch {
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
                            updateState { copy(isLoggedIn = false) }
                            emitEffect(
                                MyPageEffect.ShowSnackbar(
                                    message = result.message ?: "로그인 후 이용할 수 있습니다.",
                                    state = SnackbarState.NEGATIVE,
                                ),
                            )
                        } else {
                            Napier.d("내 프로필 이미지를 불러오는데 실패했습니다.")
                        }
                    }
            }.invokeOnCompletion {
                updateState { copy(isLoading = false) }
            }
    }

    private fun updateProfile(nickname: String, track: Track) {
        viewModelScope.launch {
            userRepository
                .updateMyProfile(nickname = nickname, track = track)
                .onSuccess {
                    updateState {
                        copy(
                            userInfo = userInfo.copy(
                                nickname = nickname,
                                track = track.toInitial(),
                            ),
                        )
                    }
                    Napier.d("프로필 수정 성공")
                    emitEffect(
                        MyPageEffect.ShowSnackbar(
                            message = "프로필이 수정되었습니다.",
                            state = SnackbarState.POSITIVE,
                        ),
                    )
                }.onFailure { result ->
                    Napier.d("프로필 수정 실패: ${result.message}")
                    emitEffect(
                        MyPageEffect.ShowSnackbar(
                            message = "프로필 수정에 실패했습니다.",
                            state = SnackbarState.NEGATIVE,
                        ),
                    )
                }
        }
    }

    private fun updateProfileImage(uri: String) {
        viewModelScope.launch {
            userRepository
                .updateMyProfileImage(uri = uri)
                .onSuccess { image: ProfileImage ->
                    updateState {
                        copy(imageUrl = image.customImageUri ?: image.basicImageUri ?: "")
                    }
                    emitEffect(
                        MyPageEffect.ShowSnackbar(
                            message = "프로필 이미지가 수정되었습니다.",
                            state = SnackbarState.POSITIVE,
                        ),
                    )
                }.onFailure { result ->
                    Napier.d("프로필 이미지 업로드 실패: ${result.message}")
                    emitEffect(
                        MyPageEffect.ShowSnackbar(
                            message = "프로필 이미지 업로드를 실패했습니다.",
                            state = SnackbarState.NEGATIVE,
                        ),
                    )
                }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            authRepository
                .logout()
                .onSuccess {
                    updateState { MyPageState() }
                    Napier.d("로그아웃 성공")
                }.onFailure { result: Throwable ->
                    Napier.w("로그아웃 실패")
                    emitEffect(
                        MyPageEffect.ShowSnackbar(
                            message = result.message ?: "로그아웃에 실패했습니다.",
                            state = SnackbarState.NEGATIVE,
                        ),
                    )
                }
        }
    }
}
