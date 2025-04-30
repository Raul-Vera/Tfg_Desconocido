package com.example.tfg

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.Configuracion.ConfigGlobal
import com.example.tfg.Modelo.AdaptadorListaLigas
import com.example.tfg.Modelo.OpcionLiga

class EleccionLigas : AppCompatActivity() {
    val listaLigas = listOf(
        OpcionLiga(
            "La Liga",
            "file:///android_asset/Ligas/loglaliga.png",
            "file:///android_asset/Paises/bespana.png",
            2
        ),
        OpcionLiga(
            "Premier League",
            "file:///android_asset/Ligas/logpremierleague.png",
            "file:///android_asset/Paises/binglaterra.png",
            1
        )
    )
    lateinit var rvListaLigas: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eleccion_ligas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        rvListaLigas = findViewById<RecyclerView>(R.id.rvlistaLigas)
        val adapter = AdaptadorListaLigas(listaLigas, this) { id ->
            val intent = Intent(this, InicioJuego::class.java)
            ConfigGlobal.seleccionarLiga(id)
            startActivity(intent)
            print("Se selecciono esta liga " + ConfigGlobal.ligaSeleccionada)
        }
        rvListaLigas.layoutManager= LinearLayoutManager(this)
        rvListaLigas.adapter=adapter
    }
}