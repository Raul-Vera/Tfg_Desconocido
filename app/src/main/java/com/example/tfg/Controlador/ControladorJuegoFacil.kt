package com.example.tfg.Controlador

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.tfg.Configuracion.ConfigGlobal
import com.example.tfg.Estadisticas.Estadisticas
import com.example.tfg.MainActivity
import com.example.tfg.Modelo.VistaJuegoFacil
import com.example.tfg.R
import com.squareup.picasso.Picasso

class ControladorJuegoFacil {
    companion object {
        var listacreada=false
        var aciertoClub=false
        var aciertoPais=false
        var aciertoEdad=false
        var aciertoPosicion=false
        var aciertoJugador=false
        lateinit var listaJugadores: MutableList<VistaJuegoFacil>
        lateinit var jugadorDesconocido: VistaJuegoFacil

        /**Genera una lista de jugadores para el modo facil
         * @return Devulve una lista conformada por jugadore para el modo facil**/
        suspend fun generarLista(): MutableList<VistaJuegoFacil> {
            ControladorJuegoFacil.listaJugadores =
                ControladorBd.juegoFacilDao.obtenerJugadoresFacil(
                    ConfigGlobal.ligaSeleccionada
                )
            listacreada=true
            return ControladorJuegoFacil.listaJugadores
        }

        /**Devuelve un jugador aleatorio
         * @return Jugador aleatorio**/
        fun elegirJugador(): VistaJuegoFacil {
            val numeroAleatorio = (1..100).random()
            for (i in 1..numeroAleatorio) {
                listaJugadores = listaJugadores.shuffled().toMutableList()
            }
            return listaJugadores.first()
        }

        fun iniciarEscuchadorPosicion(
            etPosicion: EditText,
            tvPosicion: TextView,
            context: Context
        ) {
            etPosicion.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)) {
                    val textoIngresado = etPosicion.text.toString()
                    if (ControladorJuegoFacil.comprobarPosicion(textoIngresado)) {
                        tvPosicion.text = jugadorDesconocido.posicion
                        desvelarPosicion(tvPosicion,etPosicion,context)
                    } else {
                        tvPosicion.text = "No Coincide"
                        Estadisticas.aumentarFallos()
                        tvPosicion.setTextColor(
                            ContextCompat.getColor(
                                v.context,
                                android.R.color.holo_red_light
                            )
                        )
                        etPosicion.text.clear()
                    }
                    true // Indica que manejaste la acción
                } else {
                    false // Permite otras acciones predeterminadas
                }
            }
        }

        fun comprobarPosicion(posicion: String): Boolean {
            if (posicion.equals(jugadorDesconocido.posicion, true)) {
                return true
            } else {
                return false
            }
        }

        fun iniciarEscuchadorEdad(etEdad: EditText, tvEdad: TextView, context: Context) {
            etEdad.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)) {
                    val textoIngresado = etEdad.text.toString()
                    val edadIngresada = textoIngresado.toIntOrNull()
                    val edadJugador = calcularEdad(jugadorDesconocido.edad_jugador).toIntOrNull()

                    if (edadIngresada != null && edadJugador != null) {
                        when {
                            edadIngresada == edadJugador -> {
                               desvelarEdad(tvEdad,etEdad,context)
                            }
                            edadIngresada < edadJugador -> {
                                tvEdad.text = "Mayor"
                                tvEdad.setTextColor(ContextCompat.getColor(v.context, android.R.color.holo_red_light))
                                etEdad.text.clear()
                            }
                            else -> {
                                tvEdad.text = "Menor"
                                tvEdad.setTextColor(ContextCompat.getColor(v.context, android.R.color.holo_red_light))
                                etEdad.text.clear()
                            }
                        }
                    } else {
                        tvEdad.text = "Entrada inválida"
                        tvEdad.setTextColor(ContextCompat.getColor(v.context, android.R.color.holo_red_light))
                    }
                    true
                } else {
                    false
                }
            }
        }

        fun comprobarEdad(edad: String): Boolean {
            var edadAños=calcularEdad(jugadorDesconocido.edad_jugador)
            if (edad==edadAños) {
                return true
            } else {
                return false
            }
        }
        fun iniciarEscuchadorClub(etClub: EditText, ivClub: ImageView, context: Context) {
            etClub.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)) {
                    val textoIngresado = etClub.text.toString()
                    if (ControladorJuegoFacil.comprobarClub(textoIngresado)) {
                       desvelarClub(ivClub,etClub,context)
                    } else {
                        Estadisticas.aumentarFallos()

                        etClub.text.clear()
                    }
                    true // Indica que manejaste la acción
                } else {
                    false // Permite otras acciones predeterminadas
                }
            }
        }

        fun comprobarClub(club: String): Boolean {
            if (club.equals(jugadorDesconocido.nombre_club, true)) {
                return true
            } else {
                return false
            }
        }
        fun iniciarEscuchadorPais(etPais: EditText, ivPais: ImageView, context: Context) {
            etPais.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)) {
                    val textoIngresado = etPais.text.toString()
                    if (ControladorJuegoFacil.comprobarPais(textoIngresado)) {


                       desvelarPais(ivPais,etPais,context)
                    } else {
                        Estadisticas.aumentarFallos()

                        etPais.text.clear()
                    }
                    true // Indica que manejaste la acción
                } else {
                    false // Permite otras acciones predeterminadas
                }
            }
        }

        fun comprobarPais(pais: String): Boolean {
            if (pais.equals(jugadorDesconocido.nombre_pais, true)) {
                return true
            } else {
                return false
            }
        }
        fun iniciarEscuchadorJugador(
            etJugador: EditText, ivJugador: ImageView,
            tvPosicion: TextView, etPosicion: EditText,
            tvEdad: TextView, etEdad: EditText,
            ivClub: ImageView, etClub: EditText,
            ivPais: ImageView, etPais: EditText,
            context: Context)
        {
            etJugador.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)) {
                    val textoIngresado = etJugador.text.toString()
                    if (ControladorJuegoFacil.comprobarJugador(textoIngresado)) {
                        desvelarCompleto(tvPosicion,etPosicion,tvEdad,etEdad,ivClub,etClub,ivPais,etPais,ivJugador,etJugador,context)
                    } else {
                        Estadisticas.aumentarFallos()

                        etJugador.text.clear()
                    }
                    true // Indica que manejaste la acción
                } else {
                    false // Permite otras acciones predeterminadas
                }
            }
        }

        fun comprobarJugador(nombre: String): Boolean {
            if (nombre.equals(jugadorDesconocido.apodo_jugador, true)) {
                return true
            } else {
                return false
            }
        }
        fun calcularEdad(fechaString: String): String {
            val partes = fechaString.split("-")
            if (partes.size != 3) return "-1" // Retorna -1 si el formato no es válido

            val anioNacimiento = partes[0].toIntOrNull()
            val mesNacimiento = partes[1].toIntOrNull()
            val diaNacimiento = partes[2].toIntOrNull()

            if (anioNacimiento == null || mesNacimiento == null || diaNacimiento == null) return "-1"

            val fechaActual = java.util.Date()
            val anioActual = fechaActual.year + 1900 // Se suma 1900 porque `year` devuelve años desde 1900
            val mesActual = fechaActual.month + 1   // `month` es base 0, por eso se suma 1
            val diaActual = fechaActual.date

            var edad = anioActual - anioNacimiento

            // Ajuste si aún no ha cumplido años en el año actual
            if (mesActual < mesNacimiento || (mesActual == mesNacimiento && diaActual < diaNacimiento)) {
                edad--
            }
            var edadS=edad.toString()
            return edadS
        }
        fun desvelarPosicion(tvPosicion: TextView, etPosicion: EditText,  context: Context) {
            tvPosicion.text = jugadorDesconocido.posicion
            tvPosicion.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
            etPosicion.isEnabled = false
            aciertoPosicion=true
        }

        fun desvelarEdad(tvEdad: TextView, etEdad: EditText, context: Context) {
            tvEdad.text = calcularEdad(jugadorDesconocido.edad_jugador)
            tvEdad.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
            etEdad.isEnabled = false
            aciertoEdad=true
        }

        fun desvelarClub(ivClub: ImageView, etClub: EditText, context: Context) {
            val resourceId = context.resources.getIdentifier(jugadorDesconocido.escudo_club, "drawable", context.packageName)

            if (resourceId != 0) { // Verifica que la imagen existe
                Picasso.get().load(resourceId).into(ivClub)
            } else {
                ivClub.setImageResource(R.drawable.logo1) // Imagen alternativa si no se encuentra
            }

            etClub.isEnabled = false
            aciertoClub=true
        }

        fun desvelarPais(ivPais: ImageView, etPais: EditText ,context: Context) {
            val resourceId = context.resources.getIdentifier(jugadorDesconocido.bandera_pais, "drawable", context.packageName)

            if (resourceId != 0) { // Verifica que la imagen existe
                Picasso.get().load(resourceId).into(ivPais)
            } else {
                ivPais.setImageResource(R.drawable.logo1) // Imagen alternativa si no se encuentra
            }

            etPais.isEnabled = false
            aciertoPais=true
        }

        fun desvelarJugador(ivJugador: ImageView, etJugador: EditText, context: Context) {
            val resourceId = context.resources.getIdentifier(jugadorDesconocido.foto_jugador, "drawable", context.packageName)

            if (resourceId != 0) { // Verifica que la imagen existe
                Picasso.get().load(resourceId).into(ivJugador)
            } else {
                ivJugador.setImageResource(R.drawable.logo1) // Imagen alternativa si no se encuentra
            }

            etJugador.isEnabled = false
            aciertoJugador=true
        }
        fun desvelarCompleto(
            tvPosicion: TextView, etPosicion: EditText,
            tvEdad: TextView, etEdad: EditText,
            ivClub: ImageView, etClub: EditText,
            ivPais: ImageView, etPais: EditText,
            ivJugador: ImageView, etJugador: EditText,
            context: Context
        ) {
            if(!aciertoPosicion)desvelarPosicion(tvPosicion, etPosicion, context)
            if(!aciertoEdad)desvelarEdad(tvEdad, etEdad, context)
            if(!aciertoClub)desvelarClub(ivClub, etClub, context)
            if(!aciertoPais)desvelarPais(ivPais, etPais, context)
            desvelarJugador(ivJugador, etJugador, context)
        }
        fun reiniciar(){
            listacreada=false
            Estadisticas.reiniciarFallos()
            Estadisticas.reiniciarAciertos()
        }
        fun jugadorAcertado(){
            if(listaJugadores!=null){
                listaJugadores.remove(jugadorDesconocido)
            }
            aciertoJugador=false
            aciertoClub=false
            aciertoPais=false
            aciertoEdad=false
            aciertoPosicion=false

            Estadisticas.reiniciarFallos()
            Estadisticas.aumentarAciertos()
        }
        fun pista(  tvPosicion: TextView, etPosicion: EditText,
                    tvEdad: TextView, etEdad: EditText,
                    ivClub: ImageView, etClub: EditText,
                    ivPais: ImageView, etPais: EditText,
                    ivJugador: ImageView, etJugador: EditText,contexto: Context){
            val campos=listOf<String>("Posicion","Edad","Club","Pais");
            val camposremovida=campos.shuffled()
            when(camposremovida.first()){
                "Posicion"->desvelarPosicion(tvPosicion,etPosicion,contexto);
                "Edad"->desvelarEdad(tvEdad,etEdad,contexto)
                "Club"->desvelarClub(ivClub,etClub,contexto)
                "Pais"->desvelarPais(ivPais,etPais,contexto)
            }
        }

    }

}



