package com.example.tfg.Modelo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "posiciones")
data class Posicion(
    @PrimaryKey(autoGenerate = false)
    val id_posicion: Int,
    val nombre: String,
    val abreviatura: String?
)