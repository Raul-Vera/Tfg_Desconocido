package com.example.tfg
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.tfg.Configuracion.ConfigGlobal
import com.example.tfg.Controlador.ControladorBd
import com.example.tfg.Modelo.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InicioJuego : AppCompatActivity() {
    lateinit var buIniciar: Button
    lateinit var tvLiga: TextView
    lateinit var tvModo: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio_juego)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        buIniciar = findViewById<Button>(R.id.buIniciar)
        tvModo = findViewById<TextView>(R.id.tvModo)
        tvLiga = findViewById<TextView>(R.id.tvLiga)
        var nombreL = ""
        var dificultad = ""
        when (ConfigGlobal.ligaSeleccionada) {
            1 -> nombreL = "Premier";
            2 -> nombreL = "La Liga";
        }
        if (ConfigGlobal.dificutadDificil) {
            dificultad = "dificil"
        } else {
            dificultad = "facil"
        }
        tvModo.append(dificultad)
        tvLiga.append(nombreL)
        buIniciar.setOnClickListener{
            Log.d("Button","El boton de iniciar fue pulsado")
            iniciar()
        }
    }
/**
 * Se encarga de crear la base de datos a partir de el recurso que se encuentra en assets/BBDD**/
    fun iniciar() {
        Log.d("InicioJuego", "La función iniciar() ha sido llamada")

        val facil = Intent(this, JuegoFacil::class.java)
    val dificil= Intent(this, JuegoDificil::class.java)
        lifecycleScope.launch(Dispatchers.IO) {
            ControladorBd.db = Room.databaseBuilder(applicationContext, AppDatabase::class.java,"DesconocidoBDv2.5"
            ).createFromAsset("BBDD/desconocidodbroomv2.db").build()
            Log.d("InicioJuego", "Base de datos creada correctamente")
            // Asignar vistadao en el hilo principal después de crear la base de datos
            withContext(Dispatchers.Main) {
                ControladorBd.juegoFacilDao= ControladorBd.db.juegoFacilDao()
                ControladorBd.juegoDificilDao= ControladorBd.db.juegoDificilDao()
                if (ConfigGlobal.dificutadDificil){
                    startActivity(dificil)
                }else{
                    startActivity(facil)
                }
                finish()
            }
        }
    }
}