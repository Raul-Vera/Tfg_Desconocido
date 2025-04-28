package com.example.tfg.Modelo

import androidx.room.Database

import androidx.room.RoomDatabase


@Database(
    entities = [Club::class, Competicion::class, Continente::class, Copa_continental::class,Jugador::class, Liga::class, Posicion::class, Pais::class,],
    views = [Vista_juego_clasico::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase: RoomDatabase (){

}
