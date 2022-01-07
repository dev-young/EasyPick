package io.ymsoft.easypick.features.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

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
    var content: String = "",
    val created: Long = Date().time,
    val groupId: Int? = null,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)

class InvalidCandidateException(message: String): Exception(message)

class SelectableCandidate(val candi:Candidate, var selected:Boolean = false)
