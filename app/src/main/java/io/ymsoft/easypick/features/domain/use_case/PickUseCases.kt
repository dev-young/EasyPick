package io.ymsoft.easypick.features.domain.use_case

data class PickUseCases(
    val getGroups: GetGroups,
    val getGroup: GetGroup,
    val addGroup: AddGroup,
    val addCandidate: AddCandidate,
    val deleteCandidate: DeleteCandidate,
    val getCandidatesById: GetCandidatesById
)