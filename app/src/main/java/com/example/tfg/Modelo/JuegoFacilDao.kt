package com.example.tfg.Modelo

import androidx.room.Dao
import androidx.room.Query

@Dao
interface JuegoFacilDao {
    @Query("Select * from VistaJuegoFacil where id_liga=:ligaSeleccionada")
    suspend fun obtenerJugadoresFacil(ligaSeleccionada:Int): MutableList<VistaJuegoFacil>
}