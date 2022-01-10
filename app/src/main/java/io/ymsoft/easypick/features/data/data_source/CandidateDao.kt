package io.ymsoft.easypick.features.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.ymsoft.easypick.features.domain.model.Candidate
import kotlinx.coroutines.flow.Flow

@Dao
interface CandidateDao {

    @Query("SELECT * FROM candidate")
    fun getCandidates(): Flow<List<Candidate>>

    @Query("SELECT * FROM candidate WHERE groupId = :groupId")
    fun getCandidatesByGroup(groupId: Long): Flow<List<Candidate>>

    @Query("SELECT * FROM candidate WHERE id = :id")
    suspend fun getCandidatesById(id: Int): Candidate?

    @Insert
    suspend fun insertCandidate(candi: Candidate)

    @Delete
    suspend fun deleteCandi(candi: Candidate)

    @Delete
    suspend fun deleteCandi(candidates: Array<Candidate>)
}