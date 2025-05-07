package com.example.tfg

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
import com.example.tfg.Controlador.ControladorJuego
import com.example.tfg.Controlador.Estadisticas.Estadisticas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JuegoDificil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_juego_dificil)
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
        var tvValor=findViewById<TextView>(R.id.tvValor)
        var etValor=findViewById<EditText>(R.id.etValor)
        ivIconoContinuar.isEnabled=false
        ivIconoContinuar.visibility= View.INVISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            if (!ControladorJuego.listacreada) {
                ControladorJuego.generarListaDificil()
                Log.d("ListaCreada", "${ControladorJuego.listaJugadores.size}")
            }
            ControladorJuego.jugadorDesconocido = ControladorJuego.elegirJugador()
            Log.d("Juegofacil", "${ControladorJuego.jugadorDesconocido}")
            withContext(Dispatchers.Main) {
                ControladorJuego.iniciarEscuchadorPosicion(
                    etPosicion,
                    tvPosicion,
                    this@JuegoDificil
                )
                ControladorJuego.iniciarEscuchadorEdad(etEdad, tvEdad, this@JuegoDificil)
                ControladorJuego.iniciarEscuchadorClub(etClub, ivClub, this@JuegoDificil)
                ControladorJuego.iniciarEscuchadorPais(etPais, ivPais, this@JuegoDificil)
                ControladorJuego.iniciarEscuchadorValor(etValor,tvValor,this@JuegoDificil)
                ControladorJuego.iniciarEscuchadorJugador(
                    etJugador, ivJugador,
                    tvPosicion, etPosicion,
                    tvEdad, etEdad,
                    ivClub, etClub,
                    ivPais, etPais,tvValor,
                    etValor,
                    this@JuegoDificil
                )
                ControladorJuego.pistaDificil( tvPosicion, etPosicion, tvEdad, etEdad,ivClub, etClub,ivPais, etPais,  ivJugador,etJugador,tvValor,etValor,
                    this@JuegoDificil)

                while (true) {
                    tvFallos.text = "Fallos: " + Estadisticas.fallos.toString()
                    tvAciertos.text = "Aciertos: " + Estadisticas.aciertos.toString()
                    if (ControladorJuego.aciertoJugador){
                        ivIconoContinuar.isEnabled=true
                        ivIconoContinuar.visibility=View.VISIBLE
                    }
                    if(Estadisticas.comprobarFallos()){
                        val perdido= Intent(this@JuegoDificil, PartidaPerdida::class.java)
                        startActivity(perdido)
                        finish()
                    }

                    delay(500) // Actualiza cada medio segundo para reducir consumo de recursos
                }
            }
        }
        ivIconoCasa.setOnClickListener {
            ControladorJuego.reiniciar()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        ivIconoContinuar.setOnClickListener {
            ControladorJuego.jugadorAcertado()
            Log.d("Cantidad de jugadores", ControladorJuego.listaJugadores.size.toString())
            val intent = Intent(this, JuegoDificil::class.java)
            startActivity(intent)
            finish()
        }
    }
}