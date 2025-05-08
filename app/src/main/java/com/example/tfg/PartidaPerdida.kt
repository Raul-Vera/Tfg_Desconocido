package com.example.tfg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
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
        var ivGif = findViewById<ImageView>(R.id.ivGif)
        val ivIconoCasa = findViewById<ImageView>(R.id.ivIconoCasa)
        val ivIconoSalida = findViewById<ImageView>(R.id.ivIconoSalida)
        val tvCono = findViewById<TextView>(R.id.tvCono)
        val tvRacha = findViewById<TextView>(R.id.tvRacha)
        val buReiniciar = findViewById<Button>(R.id.buReiniciar)
        val tvJugador = findViewById<TextView>(R.id.tvNombreJugador)
        val ivJugador = findViewById<ImageView>(R.id.ivJugador)
        tvRacha.append(" " + Estadisticas.aciertos+" jugadores")
        val resourceId =
            resources.getIdentifier(jugadorDesconocido.foto_jugador, "drawable", packageName)
        ivJugador.setImageResource(resourceId)
        tvJugador.text = jugadorDesconocido.apodo_jugador
        buReiniciar.setOnClickListener {
            val casa = Intent(this, Dificultad::class.java)
            ControladorJuego.reiniciar()
            startActivity(casa)
            finish()
        }
        ivIconoCasa.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        ivIconoSalida.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("¡Detente!")
                .setMessage("Me vas a hacer la de Figo?")
                .setPositiveButton("Sí,me voy a otra App") { _, _ -> finishAndRemoveTask() }
                .setNegativeButton("No,soy leal como Totti", null)
                .show()
        }
        if (Estadisticas.aciertos == 0) {
            Glide.with(this)
                .asGif()
                .load(R.drawable.cr7llorando) // o URL
                .into(ivGif)
        } else if (Estadisticas.aciertos == 1) {
            Glide.with(this)
                .asGif()
                .load(R.drawable.ney) // o URL
                .into(ivGif)
        }else if(Estadisticas.aciertos==2){
            Glide.with(this)
                .asGif()
                .load(R.drawable.cantona) // o URL
                .into(ivGif)
        }else if(Estadisticas.aciertos==3){
            Glide.with(this)
                .asGif()
                .load(R.drawable.cr7calm) // o URL
                .into(ivGif)
        }else if(Estadisticas.aciertos==4){
            Glide.with(this)
                .asGif()
                .load(R.drawable.sudafrica) // o URL
                .into(ivGif)
        }else if(Estadisticas.aciertos==5){
            Glide.with(this)
                .asGif()
                .load(R.drawable.simeone) // o URL
                .into(ivGif)
        }else if(Estadisticas.aciertos>=6 && Estadisticas.aciertos<8){
            Glide.with(this)
                .asGif()
                .load(R.drawable.iniesta) // o URL
                .into(ivGif)
        }else if (Estadisticas.aciertos>=8){
            Glide.with(this)
                .asGif()
                .load(R.drawable.mundial) // o URL
                .into(ivGif)
        }
    }
}