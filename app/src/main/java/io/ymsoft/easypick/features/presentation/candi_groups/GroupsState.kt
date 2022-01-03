package io.ymsoft.easypick.features.presentation.candi_groups

import io.ymsoft.easypick.features.domain.model.CandiGroup

data class GroupsState(
    val groups: List<CandiGroup> = emptyList()
)
