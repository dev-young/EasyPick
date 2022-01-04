package io.ymsoft.easypick.features.domain.use_case

import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.features.domain.model.InvalidCandiGroupException
import io.ymsoft.easypick.features.domain.repository.PickRepository
import kotlinx.coroutines.flow.Flow

class GetGroup(private val repo: PickRepository) {
    suspend operator fun invoke(id:Int): CandiGroup? {
        return repo.getGroupById(groupId = id)
    }
}