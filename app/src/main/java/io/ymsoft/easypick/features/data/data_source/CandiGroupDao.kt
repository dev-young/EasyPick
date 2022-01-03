package io.ymsoft.easypick.features.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.ymsoft.easypick.features.domain.model.CandiGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface CandiGroupDao {

    @Query("SELECT * FROM candigroup")
    fun getGroups(): Flow<List<CandiGroup>>

    @Query("SELECT * FROM candigroup WHERE id = :id")
    suspend fun getGroupById(id: Int): CandiGroup?

    @Insert
    suspend fun insertGroup(group: CandiGroup)

    @Delete
    suspend fun deleteGroup(group: CandiGroup)
}