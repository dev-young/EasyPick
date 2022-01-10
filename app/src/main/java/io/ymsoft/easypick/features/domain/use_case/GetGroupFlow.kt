package io.ymsoft.easypick.features.domain.use_case

import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.features.domain.model.InvalidCandiGroupException
import io.ymsoft.easypick.features.domain.repository.PickRepository
import kotlinx.coroutines.flow.Flow

class GetGroupFlow(private val repo: PickRepository) {
    operator fun invoke(id:Long): Flow<CandiGroup?> {
        return repo.getGroupFlowById(groupId = id)
    }
}