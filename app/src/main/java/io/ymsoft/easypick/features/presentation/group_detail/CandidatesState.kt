package io.ymsoft.easypick.features.presentation.group_detail

import io.ymsoft.easypick.features.domain.model.SelectableCandidate

data class CandidatesState(
    val list: List<SelectableCandidate> = emptyList(),
    val selectedCount: Int = 0,
    val isSelectedAll: Boolean = false
)
