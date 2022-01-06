package io.ymsoft.easypick.features.domain.use_case

import io.ymsoft.easypick.features.domain.model.Candidate
import io.ymsoft.easypick.features.domain.model.InvalidCandiGroupException
import io.ymsoft.easypick.features.domain.model.InvalidCandidateException
import io.ymsoft.easypick.features.domain.repository.PickRepository

class AddCandidate(private val repo: PickRepository) {
    suspend operator fun invoke(candidate: Candidate) {
        if (candidate.name.isBlank()) throw InvalidCandidateException("The name of Candidate can't be empty!")

        repo.insertCandidate(candidate)
    }
}