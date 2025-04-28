package com.example.tfg.Modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "continentes")
data class Continente(
    @PrimaryKey(autoGenerate = false)
    val id_continente:Int,
    val nombre: String
)
