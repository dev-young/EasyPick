package io.ymsoft.easypick.features.presentation.group_detail.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity

enum class SwipeDirection {
    Left,
    Initial,
    Right,
}

@ExperimentalMaterialApi
@Composable
fun SwipeableSnackbarHost(hostState: SnackbarHostState) {
    if (hostState.currentSnackbarData == null) { return }
    var size by remember { mutableStateOf(Size.Zero) }
    val swipeableState = rememberSwipeableState(SwipeDirection.Initial)
    val width = remember(size) {
        if (size.width == 0f) {
            1f
        } else {
            size.width
        }
    }
    if (swipeableState.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                when (swipeableState.currentValue) {
                    SwipeDirection.Right,
                    SwipeDirection.Left -> {
                        hostState.currentSnackbarData?.dismiss()
                    }
                    else -> {
                        return@onDispose
                    }
                }
            }
        }
    }
    val offset = with(LocalDensity.current) {
        swipeableState.offset.value.toDp()
    }
    SnackbarHost(
        hostState,
        snackbar = { snackbarData ->
            Snackbar(
                snackbarData,
                modifier = Modifier.offset(x = offset)
            )
        },
        modifier = Modifier
            .onSizeChanged { size = Size(it.width.toFloat(), it.height.toFloat()) }
            .swipeable(
                state = swipeableState,
                anchors = mapOf(
                    -width to SwipeDirection.Left,
                    0f to SwipeDirection.Initial,
                    width to SwipeDirection.Right,
                ),
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
    )
}