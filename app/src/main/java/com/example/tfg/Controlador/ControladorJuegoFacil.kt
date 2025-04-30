package com.example.tfg.Controlador

import android.R
import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.tfg.Configuracion.ConfigGlobal
import com.example.tfg.Modelo.VistaJuegoFacil
import kotlinx.coroutines.Dispatchers

class ControladorJuegoFacil {
    companion object {
        lateinit var listaJugadores: List<VistaJuegoFacil>
        lateinit var jugadorDesconocido: VistaJuegoFacil

        /**Genera una lista de jugadores para el modo facil
         * @return Devulve una lista conformada por jugadore para el modo facil**/
        suspend fun generarLista(): List<VistaJuegoFacil> {
            ControladorJuegoFacil.listaJugadores =
                ControladorBd.juegoFacilDao.obtenerJugadoresFacil(
                    ConfigGlobal.ligaSeleccionada
                )
            return ControladorJuegoFacil.listaJugadores
        }

        /**Devuelve un jugador aleatorio
         * @return Jugador aleatorio**/
        fun elegirJugador(): VistaJuegoFacil {
            val numeroAleatorio = (1..100).random()
            for (i in 1..numeroAleatorio) {
                listaJugadores = listaJugadores.shuffled()
            }
            return listaJugadores.first()
        }

        fun iniciarEscuchadorPosicion(etPosicion: EditText,tvPosicion: TextView,context: Context) {
            etPosicion.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)) {
                    val textoIngresado = etPosicion.text.toString()
                    if (ControladorJuegoFacil.comprobarPosicion(textoIngresado)) {
                        tvPosicion.text = textoIngresado
                        tvPosicion.setTextColor(ContextCompat.getColor(v.context, android.R.color.holo_green_light))
                        etPosicion.isEnabled = false
                    } else {
                        tvPosicion.text = "No Coincide"
                        tvPosicion.setTextColor(ContextCompat.getColor(v.context, android.R.color.holo_red_light))
                        etPosicion.text.clear()
                    }
                    true // Indica que manejaste la acción
                } else {
                    false // Permite otras acciones predeterminadas
                }
            }
        }
        fun comprobarPosicion(posicion: String): Boolean{
            if(posicion.equals(jugadorDesconocido.posicion,true)){
                return true
            }else{
                return false
            }
        }
    }

}