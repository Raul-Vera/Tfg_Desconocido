package com.example.tfg.Modelo

import androidx.room.Dao
import androidx.room.Query

@Dao
interface JuegoFacilDao {
    @Query("Select * from VistaJuego where id_liga=:ligaSeleccionada and valor_jugador>20")
    suspend fun obtenerJugadoresFacil(ligaSeleccionada:Int): MutableList<VistaJuego>
}