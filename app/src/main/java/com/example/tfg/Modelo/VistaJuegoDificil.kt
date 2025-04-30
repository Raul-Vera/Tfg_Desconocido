package com.example.tfg.Modelo

import androidx.room.DatabaseView

@DatabaseView("SELECT j.id_jugador AS id_jugador,\n" +
        "                      j.apodo AS apodo_jugador,\n" +
        "          j.fecha_nac as edad_jugador,\n" +
        "                         j.foto AS foto_jugador,\n" +
        "                         j.dorsal AS dorsal_jugador,\n" +
        "\t\t\t\t\t\t j.valor as valor_jugador,\n" +
        "\t\t\t\t\t\t j.partidos as partidos_jugador,\n" +
        "\t\t\t\t\t\t j.goles as goles_jugador,\n" +
        "\t\t\t\t\t\t j.asistencias as asistencias_jugador,\n" +
        "                         po.abreviatura AS posicion,\n" +
        "                         c.nombre AS nombre_club,\n" +
        "                         c.logo AS escudo_club,\n" +
        "                         p.nombre AS nombre_pais,\n" +
        "                         p.bandera AS bandera_pais,\n" +
        "                         cc.id_competicion AS id_liga\n" +
        "                         FROM jugadores AS j\n" +
        "                         JOIN posiciones AS po USING(id_posicion)\n" +
        "                         JOIN clubes AS c USING(id_club)\n" +
        "                         JOIN paises AS p USING(id_pais)\n" +
        "                        JOIN ligas AS l ON (c.id_liga = l.id_competicion)         JOIN competiciones AS cc USING(id_competicion) order by Random()")
data class VistaJuegoDificil (
    val id_jugador: Int,
    val apodo_jugador: String,
    val edad_jugador: String,
    val foto_jugador:String,
    val dorsal_jugador: String,
    val valor_jugador: Double,
    val partidos_jugador:Int,
    val goles_jugador: Int,
    val asistencias_jugador:Int,
    val posicion: String?,
    val nombre_club: String,
    val escudo_club: String,
    val nombre_pais: String,
    val bandera_pais:String,
    val id_liga: Int
)