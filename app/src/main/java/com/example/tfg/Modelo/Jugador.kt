package com.example.tfg.Modelo


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "jugadores", foreignKeys = [
    ForeignKey(Posicion::class, parentColumns = ["id_posicion"], childColumns = ["id_posicion"],onDelete = ForeignKey.CASCADE),
    ForeignKey(Club::class, parentColumns = ["id_club"], childColumns = ["id_club"],onDelete = ForeignKey.CASCADE),
    ForeignKey(Pais::class, parentColumns =["id_pais"], childColumns =["id_pais"],onDelete = ForeignKey.CASCADE)],
    indices = [
        Index(value = ["id_posicion"]),
        Index(value = ["id_club"]),
        Index(value = ["id_pais"])
    ]
)
data class Jugador(
    @PrimaryKey(autoGenerate = false)
    val id_jugador:Int,
    val nombre: String,
    val apellido1: String,
    val dorsal: Int,
    val fecha_nac: String,
    val goles: Int,
    val asistencias:Int,
    val foto: String,
    val id_posicion:Int,
    val id_pais: Int,
    val id_club: Int,
    val apodo: String,
    val valor: Double,
    val partidos:Int
)
