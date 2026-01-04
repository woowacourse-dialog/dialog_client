package com.on.dialog.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * 이미지 요청을 실행하고 결과를 렌더링하는 컴포저블. Coil의 [AsyncImage]를 Dialog 앱에 맞게 표준화한 래퍼.
 *
 * @param imageUrl 로드할 이미지의 URL.
 * @param contentDescription 접근성 서비스에서 이미지를 설명하는 데 사용되는 텍스트.
 * @param modifier 이 이미지에 적용할 [Modifier].
 * @param contentScale 이미지의 크기를 조절하는 방식. 기본값은 [ContentScale.Crop].
 * @param crossfade 이미지 로드 시 크로스페이드 애니메이션 적용 여부. 기본값은 `true`.
 * @param onSuccess 이미지 요청 성공 시 호출될 선택적 콜백.
 * @param onLoading 이미지 요청이 진행 중일 때 호출될 선택적 콜백.
 * @param onError 이미지 요청 실패 시 호출될 선택적 콜백.
 * @param placeholder 이미지 로딩 중에 표시될 선택적 [DrawableResource].
 * @param fallback [imageUrl]이 null이거나 비어있을 경우 표시될 선택적 [DrawableResource].
 * @param error 이미지 로드 중 오류 발생 시 표시될 선택적 [DrawableResource].
 */
@Composable
fun DialogAsyncImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    crossfade: Boolean = true,
    onSuccess: () -> Unit = {},
    onLoading: () -> Unit = {},
    onError: () -> Unit = {},
    placeholder: DrawableResource? = null,
    fallback: DrawableResource? = null,
    error: DrawableResource? = null,
) {
    AsyncImage(
        model =
            ImageRequest
                .Builder(LocalPlatformContext.current)
                .data(imageUrl)
                .crossfade(crossfade)
                .build(),
        placeholder = placeholder?.let { painterResource(it) },
        fallback = fallback?.let { painterResource(it) },
        error = error?.let { painterResource(it) },
        contentDescription = contentDescription,
        onSuccess = { onSuccess() },
        onLoading = { onLoading() },
        onError = { onError() },
        contentScale = contentScale,
        modifier = modifier,
    )
}
