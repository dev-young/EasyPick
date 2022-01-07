package io.ymsoft.easypick.features.presentation.group_detail

import io.ymsoft.easypick.features.domain.model.SelectableCandidate

sealed class GroupDetailEvent {
    object ToggleMode : GroupDetailEvent()
    data class ChangeName(val name: String) : GroupDetailEvent()
    data class OnCandiClick(val item: SelectableCandidate) : GroupDetailEvent()
    object AddCandidate : GroupDetailEvent()
    object Choice : GroupDetailEvent()
    object ToggleSelectAll : GroupDetailEvent()
}
