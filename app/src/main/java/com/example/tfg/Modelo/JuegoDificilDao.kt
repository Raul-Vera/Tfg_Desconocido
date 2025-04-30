package com.example.tfg.Modelo

import androidx.room.Dao
import androidx.room.Query

@Dao
interface JuegoDificilDao {
    @Query("Select * from VistaJuegoDificil where id_liga=:ligaSeleccionada")
    suspend fun obtenerJugadoresFacil(ligaSeleccionada:Int): List<VistaJuegoFacil>
}