package com.example.tfg.Controlador

import android.content.Context
import com.example.tfg.Modelo.AppDatabase
import com.example.tfg.Modelo.JuegoDificilDao
import com.example.tfg.Modelo.JuegoFacilDao


class ControladorBd() {
    companion object{
        lateinit var db: AppDatabase
        lateinit var juegoFacilDao: JuegoFacilDao
        lateinit var juegoDificilDao: JuegoDificilDao
    }
}