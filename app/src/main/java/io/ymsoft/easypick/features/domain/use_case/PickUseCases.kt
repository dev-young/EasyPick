package io.ymsoft.easypick.features.domain.use_case

data class PickUseCases(
    val getGroups: GetGroups,
    val getGroup: GetGroup,
    val getGroupFlow: GetGroupFlow,
    val addGroup: AddGroup,
    val deleteGroup: DeleteGroup,
    val addCandidate: AddCandidate,
    val deleteCandidate: DeleteCandidate,
    val getCandidatesById: GetCandidatesById
)