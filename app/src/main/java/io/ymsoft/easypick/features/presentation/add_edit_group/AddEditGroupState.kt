package io.ymsoft.easypick.features.presentation.add_edit_group

import io.ymsoft.easypick.features.domain.model.CandiGroup

data class AddEditGroupState(
    val groups: List<CandiGroup> = emptyList()
)
