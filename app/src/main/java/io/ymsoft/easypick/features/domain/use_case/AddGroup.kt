package io.ymsoft.easypick.features.domain.use_case

import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.features.domain.model.InvalidCandiGroupException
import io.ymsoft.easypick.features.domain.repository.PickRepository

class AddGroup(private val repo: PickRepository) {
    suspend operator fun invoke(group: CandiGroup) {
        if (group.name.isBlank()) throw InvalidCandiGroupException("The name of group can't be empty!")

        repo.insertCandiGroup(group)
    }
}