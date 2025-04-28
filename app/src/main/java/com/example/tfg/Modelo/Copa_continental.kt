package com.example.tfg.Modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "copas_continentales",foreignKeys = [
    ForeignKey(Competicion::class, parentColumns = ["id_competicion"], childColumns = ["id_competicion"],onDelete = ForeignKey.CASCADE),
    ForeignKey(Continente::class, parentColumns = ["id_continente"], childColumns = ["id_continente"],onDelete = ForeignKey.CASCADE)
],
    indices = [
        Index(value = ["id_competicion"]),
        Index(value = ["id_continente"])
    ]
)
data class Copa_continental (
    @PrimaryKey(autoGenerate = false)
    val id_competicion: Int,
    val id_continente: Int
)