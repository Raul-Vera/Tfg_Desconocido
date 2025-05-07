package com.example.tfg

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var buComenzar: Button
    lateinit var ivLogo: ImageView
    lateinit var tvNombre: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        buComenzar=findViewById<Button>(R.id.buComenzar)
        ivLogo=findViewById<ImageView>(R.id.ivLogo)
        tvNombre=findViewById<TextView>(R.id.tvNombre)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)

        ivLogo.startAnimation(fadeIn)
        tvNombre.startAnimation(slideUp)
        buComenzar.startAnimation(bounce)

        buComenzar.setOnClickListener {
            Comenzar()
        }
    }
    fun Comenzar(){
        val actividad= Intent(this, Dificultad::class.java)
        startActivity(actividad)
        finish()
    }
}
