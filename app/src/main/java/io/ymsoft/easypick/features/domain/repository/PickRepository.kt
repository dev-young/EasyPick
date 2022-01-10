package io.ymsoft.easypick.features.domain.repository

import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.features.domain.model.Candidate
import kotlinx.coroutines.flow.Flow

interface PickRepository {

    fun getCandiGroups(): Flow<List<CandiGroup>>

    suspend fun getGroupById(groupId: Long): CandiGroup?
    fun getGroupFlowById(groupId: Long): Flow<CandiGroup>

    suspend fun insertCandiGroup(group: CandiGroup)

    suspend fun deleteCandiGroup(group: CandiGroup)

    suspend fun deleteCandiGroup(groupId: Long)

    fun getCandidates(): Flow<List<Candidate>>

    fun getCandidates(groupId: Long): Flow<List<Candidate>>

    suspend fun getCandidateById(id: Int): Candidate?

    suspend fun insertCandidate(Candidate: Candidate)

    suspend fun deleteCandidate(Candidate: Candidate)

    suspend fun deleteCandidates(candidates: Array<Candidate>)
}