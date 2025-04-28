package com.example.tfg.Modelo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competiciones")
data class Competicion(
    @PrimaryKey (autoGenerate = false)
    val id_competicion: Int,
    val nombre: String,
    val logo: String
)
