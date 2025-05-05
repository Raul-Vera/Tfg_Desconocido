package com.example.tfg.Modelo

import androidx.room.Dao
import androidx.room.Query

@Dao
interface JuegoDificilDao {
    @Query("Select * from VistaJuego where id_liga=:ligaSeleccionada ")
    suspend fun obtenerJugadoresDificil(ligaSeleccionada:Int): MutableList<VistaJuego>

}