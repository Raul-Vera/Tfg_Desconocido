package com.example.tfg.Controlador

import android.content.Context
import android.util.TypedValue
import android.view.KeyEvent
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.tfg.Controlador.Configuracion.ConfigGlobal
import com.example.tfg.Controlador.Estadisticas.Estadisticas
import com.example.tfg.Modelo.VistaJuego
import com.example.tfg.R
import com.squareup.picasso.Picasso

class ControladorJuego {
    companion object {
        var listacreada = false
        var aciertoClub = false
        var aciertoPais = false
        var aciertoEdad = false
        var aciertoPosicion = false
        var aciertoJugador = false
        var aciertoValor = false
        lateinit var listaJugadores: MutableList<VistaJuego>
        lateinit var jugadorDesconocido: VistaJuego

        /**Genera una lista de jugadores del modo facil
         * @return Devulve una lista conformada por jugadores del modo facil**/
        suspend fun generarListaFacil(): MutableList<VistaJuego> {
            ControladorJuego.listaJugadores =
                ControladorBd.juegoFacilDao.obtenerJugadoresFacil(
                    ConfigGlobal.ligaSeleccionada
                )
            listacreada = true
            return ControladorJuego.listaJugadores
        }

        /**Genera una lista de jugadores del modo facil
         * @return Devulve una lista conformada por jugadores del modo facil**/
        suspend fun generarListaDificil(): MutableList<VistaJuego> {
            ControladorJuego.listaJugadores =
                ControladorBd.juegoDificilDao.obtenerJugadoresDificil(
                    ConfigGlobal.ligaSeleccionada
                )
            listacreada = true
            return ControladorJuego.listaJugadores
        }

        /**Devuelve un jugador aleatorio
         * @return Jugador aleatorio**/
        fun elegirJugador(): VistaJuego {
            val numeroAleatorio = (100..500).random()
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
                    if (ControladorJuego.comprobarPosicion(textoIngresado)) {
                        tvPosicion.text = jugadorDesconocido.posicion
                        desvelarPosicion(tvPosicion, etPosicion, context)
                    } else {
                        shakeEditText(etPosicion)
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
                                desvelarEdad(tvEdad, etEdad, context)
                            }

                            edadIngresada < edadJugador -> {
                                shakeEditText(etEdad)
                                Estadisticas.aumentarFallos()
                                tvEdad.text = "Mayor"
                                tvEdad.setTextColor(
                                    ContextCompat.getColor(
                                        v.context,
                                        android.R.color.holo_red_light
                                    )
                                )
                                etEdad.text.clear()

                            }

                            else -> {
                                shakeEditText(etEdad)
                                Estadisticas.aumentarFallos()
                                tvEdad.text = "Menor"
                                tvEdad.setTextColor(
                                    ContextCompat.getColor(
                                        v.context,
                                        android.R.color.holo_red_light
                                    )
                                )
                                etEdad.text.clear()
                            }
                        }
                    } else {
                        shakeEditText(etEdad)
                        Estadisticas.aumentarFallos()
                        tvEdad.text = "Entrada inválida"
                        tvEdad.setTextColor(
                            ContextCompat.getColor(
                                v.context,
                                android.R.color.holo_red_light
                            )
                        )

                    }
                    true
                } else {
                    false
                }
            }
        }

        fun comprobarEdad(edad: String): Boolean {
            var edadAños = calcularEdad(jugadorDesconocido.edad_jugador)
            if (edad == edadAños) {
                return true
            } else {
                return false
            }
        }

        fun iniciarEscuchadorValor(etValor: EditText, tvValor: TextView, context: Context) {
            etValor.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)) {
                    val valorIngresado = etValor.text.toString().toDoubleOrNull()
                    val valorJugador = jugadorDesconocido.valor_jugador.toDouble()

                    if (valorIngresado != null && valorJugador != null) {
                        when {
                            valorIngresado == valorJugador -> {
                                desvelarValor(tvValor, etValor, context)
                            }

                            valorIngresado < valorJugador -> {
                                shakeEditText(etValor)
                                tvValor.text = "Mayor"
                                tvValor.setTextColor(
                                    ContextCompat.getColor(
                                        v.context,
                                        android.R.color.holo_red_light
                                    )
                                )
                                etValor.text.clear()
                                Estadisticas.aumentarFallos()
                            }

                            else -> {
                                shakeEditText(etValor)
                                tvValor.text = "Menor"
                                tvValor.setTextColor(
                                    ContextCompat.getColor(
                                        v.context,
                                        android.R.color.holo_red_light
                                    )
                                )
                                etValor.text.clear()
                                Estadisticas.aumentarFallos()
                            }
                        }
                    } else {
                        shakeEditText(etValor)
                        tvValor.text = "Entrada inválida"
                        tvValor.setTextColor(
                            ContextCompat.getColor(
                                v.context,
                                android.R.color.holo_red_light
                            )
                        )
                        Estadisticas.aumentarFallos()
                    }
                    true
                } else {
                    false
                }
            }
        }

        fun comprobarValor(valor: String): Boolean {

            if (valor == jugadorDesconocido.valor_jugador.toString()) {
                return true
            } else {
                return false
            }
        }

        fun iniciarEscuchadorClub(etClub: EditText, ivClub: ImageView, context: Context) {
            etClub.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)) {
                    val textoIngresado = etClub.text.toString()
                    if (ControladorJuego.comprobarClub(textoIngresado)) {
                        desvelarClub(ivClub, etClub, context)
                    } else {
                        Estadisticas.aumentarFallos()
                        shakeEditText(etClub)
                        etClub.setHintTextColor(
                            ContextCompat.getColor(
                                v.context,
                                android.R.color.holo_red_light
                            )
                        )
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
                    if (ControladorJuego.comprobarPais(textoIngresado)) {
                        desvelarPais(ivPais, etPais, context)
                    } else {
                        Estadisticas.aumentarFallos()
                        shakeEditText(etPais)
                        etPais.setHintTextColor(
                            ContextCompat.getColor(
                                v.context,
                                android.R.color.holo_red_light
                            )
                        )
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
            tvValor: TextView, etValor: EditText,
            context: Context
        ) {
            etJugador.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)) {
                    val textoIngresado = etJugador.text.toString()
                    if (ControladorJuego.comprobarJugador(textoIngresado)) {
                        if (!ConfigGlobal.dificutadDificil) {
                            desvelarCompletoFacil(
                                tvPosicion,
                                etPosicion,
                                tvEdad,
                                etEdad,
                                ivClub,
                                etClub,
                                ivPais,
                                etPais,
                                ivJugador,
                                etJugador,
                                context
                            )
                        } else {
                            desvelarCompletoDificil(
                                tvPosicion,
                                etPosicion,
                                tvEdad,
                                etEdad,
                                ivClub,
                                etClub,
                                ivPais,
                                etPais,
                                ivJugador,
                                etJugador,
                                tvValor, etValor,
                                context
                            )
                        }
                    } else {
                        Estadisticas.aumentarFallos()
                        shakeEditText(etJugador)
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
            val anioActual =
                fechaActual.year + 1900 // Se suma 1900 porque `year` devuelve años desde 1900
            val mesActual = fechaActual.month + 1   // `month` es base 0, por eso se suma 1
            val diaActual = fechaActual.date

            var edad = anioActual - anioNacimiento

            // Ajuste si aún no ha cumplido años en el año actual
            if (mesActual < mesNacimiento || (mesActual == mesNacimiento && diaActual < diaNacimiento)) {
                edad--
            }
            var edadS = edad.toString()
            return edadS
        }

        fun desvelarPosicion(tvPosicion: TextView, etPosicion: EditText, context: Context) {
            tvPosicion.text = jugadorDesconocido.posicion
            tvPosicion.setTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_green_light
                )
            )
            tvPosicion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35f)
            aciertoPosicion = true
            etPosicion.hint = "Posición"
            etPosicion.isEnabled = false
            aciertoPosicion = true
        }

        fun desvelarEdad(tvEdad: TextView, etEdad: EditText, context: Context) {
            tvEdad.text = calcularEdad(jugadorDesconocido.edad_jugador)
            tvEdad.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
            etEdad.hint = "Edad"
            tvEdad.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35f)
            aciertoEdad = true
            etEdad.isEnabled = false

        }

        fun desvelarValor(tvValor: TextView, etValor: EditText, context: Context) {
            tvValor.text = jugadorDesconocido.valor_jugador.toString()
            tvValor.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
            etValor.hint = "Valor"
            etValor.isEnabled = false
            aciertoValor = true
        }

        fun desvelarClub(ivClub: ImageView, etClub: EditText, context: Context) {
            val resourceId = context.resources.getIdentifier(
                jugadorDesconocido.escudo_club,
                "drawable",
                context.packageName
            )

            if (resourceId != 0) { // Verifica que la imagen existe
                Picasso.get().load(resourceId).into(ivClub)
            } else {
                ivClub.setImageResource(R.drawable.logo1) // Imagen alternativa si no se encuentra
            }
            etClub.hint = jugadorDesconocido.nombre_club
            etClub.setHintTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.holo_green_light
                )
            )
            aciertoClub = true
            etClub.isEnabled = false

        }

        fun desvelarPais(ivPais: ImageView, etPais: EditText, context: Context) {
            val resourceId = context.resources.getIdentifier(
                jugadorDesconocido.bandera_pais,
                "drawable",
                context.packageName
            )

            if (resourceId != 0) { // Verifica que la imagen existe
                Picasso.get().load(resourceId).into(ivPais)
                etPais.setHintTextColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.holo_green_light
                    )
                )
            } else {
                ivPais.setImageResource(R.drawable.logotrans) // Imagen alternativa si no se encuentra
            }
            etPais.hint = jugadorDesconocido.nombre_pais
            aciertoPais = true
            etPais.isEnabled = false

        }

        fun desvelarJugador(ivJugador: ImageView, etJugador: EditText, context: Context) {
            val resourceId = context.resources.getIdentifier(
                jugadorDesconocido.foto_jugador,
                "drawable",
                context.packageName
            )

            if (resourceId != 0) { // Verifica que la imagen existe
                Picasso.get().load(resourceId).into(ivJugador)
            } else {
                ivJugador.setImageResource(R.drawable.logo1) // Imagen alternativa si no se encuentra
            }

            etJugador.isEnabled = false
            aciertoJugador = true
        }

        fun desvelarCompletoFacil(
            tvPosicion: TextView, etPosicion: EditText,
            tvEdad: TextView, etEdad: EditText,
            ivClub: ImageView, etClub: EditText,
            ivPais: ImageView, etPais: EditText,
            ivJugador: ImageView, etJugador: EditText,
            context: Context
        ) {
            if (!aciertoPosicion) desvelarPosicion(tvPosicion, etPosicion, context)
            if (!aciertoEdad) desvelarEdad(tvEdad, etEdad, context)
            if (!aciertoClub) desvelarClub(ivClub, etClub, context)
            if (!aciertoPais) desvelarPais(ivPais, etPais, context)
            desvelarJugador(ivJugador, etJugador, context)
        }

        fun desvelarCompletoDificil(
            tvPosicion: TextView, etPosicion: EditText,
            tvEdad: TextView, etEdad: EditText,
            ivClub: ImageView, etClub: EditText,
            ivPais: ImageView, etPais: EditText,
            ivJugador: ImageView, etJugador: EditText,
            tvValor: TextView, etValor: EditText,
            context: Context
        ) {
            if (!aciertoPosicion) desvelarPosicion(tvPosicion, etPosicion, context)
            if (!aciertoEdad) desvelarEdad(tvEdad, etEdad, context)
            if (!aciertoClub) desvelarClub(ivClub, etClub, context)
            if (!aciertoPais) desvelarPais(ivPais, etPais, context)
            if (!aciertoValor) desvelarValor(tvValor, etValor, context)
            desvelarJugador(ivJugador, etJugador, context)
        }

        fun reiniciar() {
            listacreada = false
            aciertoJugador = false
            aciertoClub = false
            aciertoPais = false
            aciertoEdad = false
            aciertoPosicion = false
            aciertoValor = false
            Estadisticas.reiniciarFallos()
            Estadisticas.reiniciarAciertos()
        }

        fun jugadorAcertado() {
            if (listaJugadores != null) {
                listaJugadores.remove(jugadorDesconocido)
            }
            aciertoJugador = false
            aciertoClub = false
            aciertoPais = false
            aciertoEdad = false
            aciertoPosicion = false
            aciertoValor = false

            Estadisticas.reiniciarFallos()
            Estadisticas.aumentarAciertos()
        }

        fun pistaFacil(
            tvPosicion: TextView, etPosicion: EditText,
            tvEdad: TextView, etEdad: EditText,
            ivClub: ImageView, etClub: EditText,
            ivPais: ImageView, etPais: EditText,
            ivJugador: ImageView, etJugador: EditText, contexto: Context
        ) {
            val campos = listOf<String>("Posicion", "Edad", "Club", "Pais");
            val camposremovida = campos.shuffled()
            when (camposremovida.first()) {
                "Posicion" -> desvelarPosicion(tvPosicion, etPosicion, contexto);
                "Edad" -> desvelarEdad(tvEdad, etEdad, contexto)
                "Club" -> desvelarClub(ivClub, etClub, contexto)
                "Pais" -> desvelarPais(ivPais, etPais, contexto)
            }
        }

        fun pistaDificil(
            tvPosicion: TextView,
            etPosicion: EditText,
            tvEdad: TextView,
            etEdad: EditText,
            ivClub: ImageView,
            etClub: EditText,
            ivPais: ImageView,
            etPais: EditText,
            ivJugador: ImageView,
            etJugador: EditText,
            tvValor: TextView,
            etValor: EditText,
            contexto: Context
        ) {
            val campos = listOf<String>("Posicion", "Edad", "Club", "Pais", "Valor");
            val camposremovida = campos.shuffled()
            when (camposremovida.first()) {
                "Posicion" -> desvelarPosicion(tvPosicion, etPosicion, contexto);
                "Edad" -> desvelarEdad(tvEdad, etEdad, contexto)
                "Club" -> desvelarClub(ivClub, etClub, contexto)
                "Pais" -> desvelarPais(ivPais, etPais, contexto)
                "Valor" -> desvelarValor(tvValor, etValor, contexto)
            }
        }

        fun shakeEditText(editText: EditText) {
            val shake = AnimationUtils.loadAnimation(editText.context, R.anim.shake)
            editText.startAnimation(shake)
        }
    }

}



