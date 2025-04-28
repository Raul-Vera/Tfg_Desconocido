package com.example.tfg.Modelo

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [Club::class, Competicion::class, Continente::class, Copa_continental::class,Jugador::class, Copa_continental::class, Continente::class, Competicion::class, Club::class],
    views = [],
    version = 1
)
class AppDatabase: RoomDatabase {
}