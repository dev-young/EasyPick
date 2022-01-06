package io.ymsoft.easypick.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ymsoft.easypick.features.data.data_source.EasyPickDatabase
import io.ymsoft.easypick.features.data.repository.PickRepositoryImpl
import io.ymsoft.easypick.features.domain.repository.PickRepository
import io.ymsoft.easypick.features.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): EasyPickDatabase {
        return Room.databaseBuilder(
            app,
            EasyPickDatabase::class.java,
            EasyPickDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: EasyPickDatabase): PickRepository {
        return PickRepositoryImpl(db.groupDao, db.candidateDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: PickRepository): PickUseCases {
        return PickUseCases(
            addCandidate = AddCandidate(repository),
            addGroup = AddGroup(repository),
            deleteCandidate = DeleteCandidate(repository),
            getGroups = GetGroups(repository),
            getGroup = GetGroup(repository),
            getCandidatesById = GetCandidatesById(repository)
        )
    }
}