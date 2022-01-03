package io.ymsoft.easypick.features.domain.use_case

import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.features.domain.model.InvalidCandiGroupException
import io.ymsoft.easypick.features.domain.repository.PickRepository
import kotlinx.coroutines.flow.Flow

class GetGroups(private val repo: PickRepository) {
    operator fun invoke(): Flow<List<CandiGroup>> {
        return repo.getCandiGroups()
    }
}