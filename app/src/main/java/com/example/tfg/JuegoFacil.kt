package com.example.tfg

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
import kotlinx.coroutines.delay
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
        var ivIconoCasa = findViewById<ImageView>(R.id.ivIconoCasa)
        var ivIconoContinuar = findViewById<ImageView>(R.id.ivIconoContinuar)
        var tvFallos = findViewById<TextView>(R.id.tvFallos)
        var tvAciertos = findViewById<TextView>(R.id.tvAciertos)
        var etJugador = findViewById<EditText>(R.id.etJugador)
        var ivJugador = findViewById<ImageView>(R.id.ivJugador)
        var ivClub = findViewById<ImageView>(R.id.ivClub)
        var ivPais = findViewById<ImageView>(R.id.ivPais)
        var etEdad = findViewById<EditText>(R.id.etEdad)
        var etPosicion = findViewById<EditText>(R.id.etPosicion)
        var etClub = findViewById<EditText>(R.id.etClub)
        var etPais = findViewById<EditText>(R.id.etPais)
        var tvEdad = findViewById<TextView>(R.id.tvEdad)
        var tvPosicion = findViewById<TextView>(R.id.tvPosicion)
        ivIconoContinuar.isEnabled=false
        ivIconoContinuar.visibility= View.INVISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            if (!ControladorJuegoFacil.listacreada) {
                ControladorJuegoFacil.generarLista()
                Log.d("ListaCreada", "${ControladorJuegoFacil.listaJugadores.size}")
            }
            ControladorJuegoFacil.jugadorDesconocido = ControladorJuegoFacil.elegirJugador()
            Log.d("Juegofacil", "${ControladorJuegoFacil.jugadorDesconocido}")
            withContext(Dispatchers.Main) {
                ControladorJuegoFacil.iniciarEscuchadorPosicion(
                    etPosicion,
                    tvPosicion,
                    this@JuegoFacil
                )
                ControladorJuegoFacil.iniciarEscuchadorEdad(etEdad, tvEdad, this@JuegoFacil)
                ControladorJuegoFacil.iniciarEscuchadorClub(etClub, ivClub, this@JuegoFacil)
                ControladorJuegoFacil.iniciarEscuchadorPais(etPais, ivPais, this@JuegoFacil)
                ControladorJuegoFacil.iniciarEscuchadorJugador(
                    etJugador, ivJugador,
                    tvPosicion, etPosicion,
                    tvEdad, etEdad,
                    ivClub, etClub,
                    ivPais, etPais,
                    this@JuegoFacil
                )
                ControladorJuegoFacil.pista( tvPosicion, etPosicion, tvEdad, etEdad,ivClub, etClub,ivPais, etPais,  ivJugador,etJugador,
                    this@JuegoFacil)

                while (true) {
                    tvFallos.text = "Fallos: " + Estadisticas.fallos.toString()
                    tvAciertos.text = "Aciertos: " + Estadisticas.aciertos.toString()
                    if (ControladorJuegoFacil.aciertoJugador){
                        ivIconoContinuar.isEnabled=true
                        ivIconoContinuar.visibility=View.VISIBLE
                    }
                    if(Estadisticas.comprobarFallos()){
                        val perdido= Intent(this@JuegoFacil, PartidaPerdida::class.java)
                        startActivity(perdido)
                        finish()
                    }

                    delay(500) // Actualiza cada medio segundo para reducir consumo de recursos
                }
            }
        }
        ivIconoCasa.setOnClickListener {
            ControladorJuegoFacil.reiniciar()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        ivIconoContinuar.setOnClickListener {
            ControladorJuegoFacil.jugadorAcertado()
            Log.d("Cantidad de jugadores", ControladorJuegoFacil.listaJugadores.size.toString())
            val intent = Intent(this, JuegoFacil::class.java)
            startActivity(intent)
            finish()
        }
    }

}
