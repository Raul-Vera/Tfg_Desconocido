package com.example.tfg.Modelo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "paises", foreignKeys = [
    ForeignKey(Continente::class, parentColumns =["id_continente"], childColumns = ["id_continente"],onDelete = ForeignKey.CASCADE )
],
    indices = [
        Index(value = ["id_continente"])
    ]
)
data class Pais(
    @PrimaryKey (autoGenerate = false)
    val id_pais: Int,
    val nombre: String,
    val bandera: String,
    val id_continente: Int
)
