package com.example.tfg.Controlador

import com.example.tfg.Modelo.AppDatabase
import com.example.tfg.Modelo.JuegoFacilDao


class ControladorBd() {
    companion object{
        lateinit var db: AppDatabase
        lateinit var juegoFacilDao: JuegoFacilDao
    }
}