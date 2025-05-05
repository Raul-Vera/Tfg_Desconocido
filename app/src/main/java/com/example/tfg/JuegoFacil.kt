package com.example.tfg

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.tfg.Configuracion.ConfigGlobal
import com.example.tfg.Controlador.ControladorBd
import com.example.tfg.Controlador.ControladorJuegoFacil
import com.example.tfg.Estadisticas.Estadisticas
import com.example.tfg.Modelo.VistaJuegoFacil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JuegoFacil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_juego_facil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var etJugador=findViewById<EditText>(R.id.etJugador)
        var ivJugador=findViewById<ImageView>(R.id.ivJugador)
        var ivClub=findViewById<ImageView>(R.id.ivClub)
        var ivPais=findViewById<ImageView>(R.id.ivPais)
        var etEdad=findViewById<EditText>(R.id.etEdad)
        var etPosicion=findViewById<EditText>(R.id.etPosicion)
        var etClub=findViewById<EditText>(R.id.etClub)
        var etPais=findViewById<EditText>(R.id.etPais)
        var tvEdad=findViewById<TextView>(R.id.tvEdad)
        var tvPosicion=findViewById<TextView>(R.id.tvPosicion)
        lifecycleScope.launch(Dispatchers.IO) {
            if(!ControladorJuegoFacil.listacreada) {
                ControladorJuegoFacil.generarLista()
                Log.d("ListaCreada","${ControladorJuegoFacil.listaJugadores.size}")
            }
            ControladorJuegoFacil.jugadorDesconocido= ControladorJuegoFacil.elegirJugador()
            Log.d("Juegofacil","${ControladorJuegoFacil.jugadorDesconocido}")
            withContext (Dispatchers.Main){
                ControladorJuegoFacil.iniciarEscuchadorPosicion(etPosicion,tvPosicion,this@JuegoFacil)
                ControladorJuegoFacil.iniciarEscuchadorEdad(etEdad,tvEdad,this@JuegoFacil)
                ControladorJuegoFacil.iniciarEscuchadorClub(etClub,ivClub,this@JuegoFacil)
                ControladorJuegoFacil.iniciarEscuchadorPais(etPais,ivPais,this@JuegoFacil)
                ControladorJuegoFacil.iniciarEscuchadorJugador(etJugador, ivJugador,
                    tvPosicion, etPosicion,
                    tvEdad, etEdad,
                    ivClub, etClub,
                    ivPais, etPais,
                    this@JuegoFacil)
            }
        }

    }
}