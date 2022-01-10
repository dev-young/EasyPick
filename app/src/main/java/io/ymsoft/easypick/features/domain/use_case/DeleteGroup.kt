package io.ymsoft.easypick.features.domain.use_case

import io.ymsoft.easypick.features.domain.repository.PickRepository

class DeleteGroup(private val repo: PickRepository) {
    suspend operator fun invoke(groupId:Long) {
        repo.deleteCandiGroup(groupId)
    }
}