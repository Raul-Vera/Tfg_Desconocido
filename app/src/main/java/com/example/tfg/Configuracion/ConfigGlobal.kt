package com.example.tfg.Configuracion

class ConfigGlobal {
    companion object{
        var dificutadDificil=false;
        var ligaSeleccionada:Int=0
        fun cambiarDificultad(elemento: Boolean){
            dificutadDificil=elemento
        }
        fun seleccionarLiga(id:Int){
            ligaSeleccionada=id
        }
    }
}