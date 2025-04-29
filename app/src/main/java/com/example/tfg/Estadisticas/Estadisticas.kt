package com.example.tfg.Estadisticas

class Estadisticas {
    companion object{
        var aciertos=0;
        var fallos=0;
        /**
         * Aumenta el nº de aciertos
         * **/
        fun aumentarAciertos(){
            aciertos++
        }
        /**Aumenta el nº de fallos**/
        fun aumentarFallos(){
            fallos++
        }
        /**
         * Comprueba si hay mas de 10 fallos, en ese caso el jugador perderia y se reiniciaria el nº de aciertos
         * @return Devuelve true si el juego termina y false si continua**/
        fun comprobarFallos(): Boolean{
            if(fallos>10){
                reiniciarFallos()
                return true
            }else{
                return false
            }
        }
        /**
         * Reinicia el nº de fallos**/
        fun reiniciarFallos(){
            fallos=0
        }
        /**
         * Reinicia el nº de aciertos**/
        fun reiniciarAciertos(){
            aciertos=0;
        }
    }
}