package com.example.tfg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tfg.Controlador.ControladorJuego
import com.example.tfg.Controlador.ControladorJuego.Companion.jugadorDesconocido
import com.example.tfg.Controlador.Estadisticas.Estadisticas

class PartidaPerdida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_partida_perdida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tvCono=findViewById<TextView>(R.id.tvCono)
        val tvRacha=findViewById<TextView>(R.id.tvRacha)
        val buReiniciar=findViewById<Button>(R.id.buReiniciar)
        val tvJugador=findViewById<TextView>(R.id.tvNombreJugador)
        val ivJugador=findViewById<ImageView>(R.id.ivJugador)
        tvRacha.append(" "+ Estadisticas.aciertos)
        val resourceId = resources.getIdentifier(jugadorDesconocido.foto_jugador, "drawable", packageName)
        ivJugador.setImageResource(resourceId)
        tvJugador.text=jugadorDesconocido.apodo_jugador
        buReiniciar.setOnClickListener {
            val casa= Intent(this, Dificultad::class.java)
            ControladorJuego.reiniciar()
            startActivity(casa)
            finish()
        }
    }
}