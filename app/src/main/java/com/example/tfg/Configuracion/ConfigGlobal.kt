package com.example.tfg.Configuracion

class ConfigGlobal {
    companion object{
        var dificutadDificil=false;
        var ligaSeleccionada:Int=0
        /**
         * Indica la dificultad del juego
         @param True si se cambia de facil a dificil y false si es al reves
         **/
         fun  cambiarDificultad(elemento: Boolean){
            dificutadDificil=elemento
        }
        /**
         * Permite cambiar la liga seleccionada para el juego
         * @param El id de la liga a la que se cambia
         * **/
        fun seleccionarLiga(id:Int){
            ligaSeleccionada=id
        }
    }
}