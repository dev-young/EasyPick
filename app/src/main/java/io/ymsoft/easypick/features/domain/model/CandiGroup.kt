package io.ymsoft.easypick.features.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CandiGroup(
    var name: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)

class InvalidCandiGroupException(message: String): Exception(message)
