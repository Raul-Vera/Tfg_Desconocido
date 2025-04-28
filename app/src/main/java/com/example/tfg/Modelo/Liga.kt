package com.example.tfg.Modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "ligas", foreignKeys = [
    ForeignKey(Competicion::class, parentColumns = ["id_competicion"], childColumns = ["id_competicion"],onDelete = ForeignKey.CASCADE),
    ForeignKey(Pais::class, parentColumns = ["id_pais"], childColumns = ["id_pais"],onDelete = ForeignKey.CASCADE)
])
data class Liga(
    @PrimaryKey(autoGenerate = false)
    val id_competicion: Int,
    val id_pais: Int
)

