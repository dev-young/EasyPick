package io.ymsoft.easypick.features.data.repository

import io.ymsoft.easypick.features.data.data_source.CandiGroupDao
import io.ymsoft.easypick.features.data.data_source.CandidateDao
import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.features.domain.model.Candidate
import io.ymsoft.easypick.features.domain.repository.PickRepository
import kotlinx.coroutines.flow.Flow

class PickRepositoryImpl(
    private val groupDao: CandiGroupDao,
    private val candiDao: CandidateDao
) : PickRepository {

    override fun getCandiGroups(): Flow<List<CandiGroup>> {
        return groupDao.getGroups()
    }

    override suspend fun getGroupById(groupId: Int): CandiGroup? {
        return groupDao.getGroupById(groupId)
    }

    override fun getGroupFlowById(groupId: Int): Flow<CandiGroup> {
        return groupDao.getGroupFlowById(groupId)
    }

    override suspend fun insertCandiGroup(group: CandiGroup) {
        groupDao.insertGroup(group)
    }

    override suspend fun deleteCandiGroup(group: CandiGroup) {
        groupDao.deleteGroup(group)
    }

    override suspend fun deleteCandiGroup(groupId: Int) {
        groupDao.deleteGroup(groupId)
    }

    override fun getCandidates(): Flow<List<Candidate>> {
        return candiDao.getCandidates()
    }

    override fun getCandidates(groupId: Int): Flow<List<Candidate>> {
        return candiDao.getCandidatesByGroup(groupId)
    }

    override suspend fun getCandidateById(id: Int): Candidate? {
        return candiDao.getCandidatesById(id)
    }

    override suspend fun insertCandidate(candidate: Candidate) {
        candiDao.insertCandidate(candidate)
    }

    override suspend fun deleteCandidate(candidate: Candidate) {
        candiDao.deleteCandi(candidate)
    }

    override suspend fun deleteCandidates(candidates: Array<Candidate>) {
        candiDao.deleteCandi(candidates)
    }
}