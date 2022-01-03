package io.ymsoft.easypick.features.domain.use_case

import io.ymsoft.easypick.features.domain.model.Candidate
import io.ymsoft.easypick.features.domain.repository.PickRepository

class DeleteCandidate(private val repo: PickRepository) {
    suspend operator fun invoke(candidate: Candidate) {
        repo.deleteCandidate(candidate)
    }
}