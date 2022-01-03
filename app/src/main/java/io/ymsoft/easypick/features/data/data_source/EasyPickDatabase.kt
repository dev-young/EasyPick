package io.ymsoft.easypick.features.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import io.ymsoft.easypick.features.domain.model.CandiGroup
import io.ymsoft.easypick.features.domain.model.Candidate

@Database(
    entities = [Candidate::class, CandiGroup::class],
    version = 1
)
abstract class EasyPickDatabase: RoomDatabase() {

    abstract val groupDao: CandiGroupDao
    abstract val candidateDao: CandidateDao

    companion object {
        const val DATABASE_NAME = "EasyPick_db"
    }
}