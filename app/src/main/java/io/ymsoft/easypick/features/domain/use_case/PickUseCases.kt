package io.ymsoft.easypick.features.domain.use_case

data class PickUseCases(
    val getGroups: GetGroups,
    val addGroup: AddGroup,
    val addCandidate: AddCandidate,
    val deleteCandidate: DeleteCandidate
)