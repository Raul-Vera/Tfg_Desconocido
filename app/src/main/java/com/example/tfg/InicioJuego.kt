package com.example.tfg
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.tfg.Controlador.Configuracion.ConfigGlobal
import com.example.tfg.Controlador.ControladorBd
import com.example.tfg.Modelo.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class InicioJuego : AppCompatActivity() {
    var frasesFutbol = listOf(
        "Me cortaron las piernas." to "Diego Maradona",
        "Jugar al fútbol es muy sencillo, pero jugar un fútbol sencillo es lo más difícil que hay." to "Johan Cruyff",
        "No necesito el Balón de Oro para saber que soy el mejor." to "Zlatan Ibrahimović",
        "Siempre me dicen que sonría, y yo siempre sonrío. Es mi forma de jugar." to "Ronaldinho",
        "Cuando marco, no celebro porque sólo estoy haciendo mi trabajo. ¿Acaso el cartero celebra cuando entrega una carta?" to "Mario Balotelli",
        "Nunca aprendí inglés porque nunca voy a ir a jugar al extranjero. Roma es mi casa." to "Francesco Totti",
        "El fútbol es un juego simple: 22 hombres persiguen un balón durante 90 minutos y al final siempre gana Alemania." to "Gary Lineker",
        "Cuanto más difícil es la victoria, mayor es la felicidad de ganar." to "Pelé",
        "Cuando me insultaban por ser negro, les respondía marcando goles." to "Samuel Eto’o",
        "Si me cierran la puerta, entro por la ventana. Y si no hay ventana, rompo la pared." to "Dani Alves",
        "Yo no soy un 10, soy un 9 y medio." to "Iván Zamorano",
        "En mi vida me he comido más pasteles que defensas." to "Antonio Cassano",
        "Cuando las gaviotas siguen al barco es porque piensan que lanzarán sardinas al mar." to "Eric Cantona",
        "La gente me odia porque soy guapo, rico y buen jugador." to "Cristiano Ronaldo",
        "Prefiero ganar títulos con el equipo antes que premios individuales." to "Lionel Messi",
        "Estoy feliz porque las cosas están saliendo bien, incluso bajo las circunstancias que estamos." to "Jared Borgetti",
        "A veces uno quiere hacer cosas y no le salen, como cuando quieres ir al baño y no puedes." to "Cuauhtémoc Blanco",
        "Brasil practica el juego bonito, pero nosotros practicamos el ganar bonito." to "Luis Suárez",
        "En el fútbol a veces se gana, a veces se pierde, y a veces te empatan." to "Sergio Ramos",
        "No sé, no me acuerdo. No estaba, y si estaba, estaba dormido." to "Diego Armando Maradona",
        "Yo no fui, fue el VAR." to "Gerard Piqué",
        "Perdimos porque no ganamos." to "Ronaldo Nazário",
        "Mi mamá me dijo que no dijera malas palabras, así que le dije al árbitro que era un incompetente profesional." to "Zlatan Ibrahimović",
        "No quiero parecer arrogante, pero soy el mejor jugador del mundo." to "Cristiano Ronaldo",
        "Le pegué tan fuerte que si no entraba mataba a alguien en la tribuna." to "Carlos Tévez",
        "Yo siempre decía: si no fuera tan bueno jugando, sería modelo." to "David Beckham",
        "No sé qué pasó, salimos todos a jugar y de pronto ya íbamos perdiendo." to "Carlos Valderrama",
        "Mi mujer me preguntó si prefería el fútbol o ella… y extraño un poco a mi mujer." to "Mario Balotelli",
        "¿Jugar bajo la lluvia? Yo cobro igual, que llueva o no." to "Neymar Jr.",
        "No entiendo cómo el árbitro no vio ese penal. Yo lo vi clarito desde mi casa cuando vi la repetición." to "Dani Alves",
        "Y no es un insulto,es un adjetivo calificativo." to "Pichu Cuellar"
    )
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
        var tvCita=findViewById<TextView>(R.id.tvCita)
        var tvAutor=findViewById<TextView>(R.id.tvAutor)
        var ivIconoCasa = findViewById<ImageView>(R.id.ivIconoCasa)
        var ivIconoSalida = findViewById<ImageView>(R.id.ivIconoSalida)
        buIniciar = findViewById<Button>(R.id.buIniciar)
        tvModo = findViewById<TextView>(R.id.tvModo)
        mostrarFraseAleatoria(tvCita,tvAutor)
        val moverderecha = AnimationUtils.loadAnimation(this,R.anim.mover_der)
        var nombreL = ""
        var dificultad = ""
        when (ConfigGlobal.ligaSeleccionada) {
            1 -> nombreL = "Premier";
            2 -> nombreL = "La Liga";
        }
        if (ConfigGlobal.dificutadDificil) {
            dificultad = "Dificil"
        } else {
            dificultad = "Facil"
        }
        tvModo.text="Modo "+dificultad+"   Liga : "+nombreL
        tvModo.startAnimation(moverderecha)
        buIniciar.setOnClickListener{
            Log.d("Button","El boton de iniciar fue pulsado")
            iniciar()
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
    fun mostrarFraseAleatoria(textViewCita: TextView, textViewAutor: TextView) {
        frasesFutbol=frasesFutbol.shuffled()
        val (frase, autor) = frasesFutbol.random()
        val citaFormateada = "\"$frase\""
        textViewCita.text = citaFormateada
        textViewAutor.text = autor
    }
}