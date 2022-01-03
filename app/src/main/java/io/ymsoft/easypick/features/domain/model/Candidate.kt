package io.ymsoft.easypick.features.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = CandiGroup::class,
        parentColumns = ["id"],
        childColumns = ["groupId"],
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Candidate(
    var name: String,
    val created: Long,
    var content: String,
    val groupId: Int? = null,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
