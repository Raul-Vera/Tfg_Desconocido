package com.example.tfg

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tfg.Modelo.OpcionLiga

class EleccionLigas : AppCompatActivity() {
    val listaLigas=listOf(
        OpcionLiga("La Liga","file:///android_asset/Ligas/loglaliga.png","file:///android_asset/Paises/bespaña",2),
        OpcionLiga("Premier League","file:///android_asset/Ligas/logpremierleague.png","file:///android_asset/Paises/binglaterra",1)
        )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eleccion_ligas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}