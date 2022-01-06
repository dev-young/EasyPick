package io.ymsoft.easypick.features.presentation.group_detail

sealed class GroupDetailEvent {
    object ToggleMode : GroupDetailEvent()
    data class ChangeName(val name: String) : GroupDetailEvent()
    object AddCandidate : GroupDetailEvent()
    object Choice : GroupDetailEvent()
}
