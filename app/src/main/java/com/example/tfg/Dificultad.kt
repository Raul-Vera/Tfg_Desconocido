package com.example.tfg

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tfg.Controlador.Configuracion.ConfigGlobal
import com.example.tfg.Controlador.ControladorJuego

class Dificultad : AppCompatActivity() {
    lateinit var buFacil: Button
    lateinit var buDificil: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dificultad)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var ivIconoCasa = findViewById<ImageView>(R.id.ivIconoCasa)
        buFacil=findViewById<Button>(R.id.buFacil)
        buDificil=findViewById<Button>(R.id.buDificil)
        buFacil.setOnClickListener {
            ConfigGlobal.cambiarDificultad(false)
            Log.d("Dificultad","Valor de dificultadDificil "+ConfigGlobal.dificutadDificil)
            startActivity(Intent(this, EleccionLigas::class.java))
            finish()
        }
        buDificil.setOnClickListener {
            ConfigGlobal.cambiarDificultad(true)
            Log.d("Dificultad","Valor de dificultadDificil "+ConfigGlobal.dificutadDificil)
            startActivity(Intent(this, EleccionLigas::class.java))
            finish()
        }
        ivIconoCasa.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}