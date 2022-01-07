package io.ymsoft.easypick.features.data.data_source

import androidx.room.*
import io.ymsoft.easypick.features.domain.model.CandiGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface CandiGroupDao {

    @Query("SELECT * FROM candigroup")
    fun getGroups(): Flow<List<CandiGroup>>

    @Query("SELECT * FROM candigroup WHERE id = :id")
    suspend fun getGroupById(id: Int): CandiGroup?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: CandiGroup)

    @Delete
    suspend fun deleteGroup(group: CandiGroup)

    @Query("DELETE FROM candigroup WHERE id = :groupId")
    suspend fun deleteGroup(groupId: Int)
}