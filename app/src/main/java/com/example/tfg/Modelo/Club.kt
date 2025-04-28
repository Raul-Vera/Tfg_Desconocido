package com.example.tfg.Modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "clubes", foreignKeys = [
    ForeignKey(Liga::class, parentColumns = ["id_competicion"], childColumns = ["id_liga"], onDelete = ForeignKey.CASCADE),
        ForeignKey(Copa_continental::class, parentColumns = ["id_competicion"], childColumns = ["id_copa_continental"],onDelete = ForeignKey.CASCADE)
],
    indices = [
        Index(value = ["id_liga"]),
        Index(value = ["id_copa_continental"])
    ]
)
data class Club(
    @PrimaryKey(autoGenerate = false)
    val id_club: Int,
    val nombre: String,
    val logo: String,
    val id_liga: Int,
    val id_copa_continental: Int?
)
