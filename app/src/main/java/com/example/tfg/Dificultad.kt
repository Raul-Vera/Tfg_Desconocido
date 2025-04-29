package com.example.tfg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tfg.Configuracion.ConfigGlobal

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
        buFacil=findViewById<Button>(R.id.buFacil)
        buDificil=findViewById<Button>(R.id.buDificil)
        buFacil.setOnClickListener {
            ConfigGlobal.cambiarDificultad(false)
            startActivity(Intent(this, EleccionLigas::class.java))
        }
        buDificil.setOnClickListener {
            ConfigGlobal.cambiarDificultad(true)
            startActivity(Intent(this, EleccionLigas::class.java))
        }
    }
}