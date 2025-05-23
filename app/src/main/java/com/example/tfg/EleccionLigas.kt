package com.example.tfg

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.Controlador.Configuracion.ConfigGlobal
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
        var ivIconoCasa = findViewById<ImageView>(R.id.ivIconoCasa)
        var ivIconoSalida = findViewById<ImageView>(R.id.ivIconoSalida)
        rvListaLigas = findViewById<RecyclerView>(R.id.rvlistaLigas)
        val adapter = AdaptadorListaLigas(listaLigas, this) { id ->
            val intent = Intent(this, InicioJuego::class.java)
            ConfigGlobal.seleccionarLiga(id)
            Log.d("Selección liga","Se ha seleccionado la liga"+ ConfigGlobal.ligaSeleccionada)
            startActivity(intent)
            finish()
            print("Se selecciono esta liga " + ConfigGlobal.ligaSeleccionada)
        }
        rvListaLigas.layoutManager= LinearLayoutManager(this)
        rvListaLigas.adapter=adapter
        ivIconoCasa.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        ivIconoSalida.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("¡Detente!")
                .setMessage("Me vas a hacer la de Figo?")
                .setPositiveButton("Sí,me voy a otra App") { _, _ -> finishAndRemoveTask() } // Cierra la aplicación
                .setNegativeButton("No,soy leal como Totti", null) // No hace nada, simplemente cierra el diálogo
                .show()
        }
    }
}