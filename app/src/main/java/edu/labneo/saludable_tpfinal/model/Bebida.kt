package edu.labneo.saludable_tpfinal.model

import java.io.Serializable
//Data class para mapear el Json de la API.
data class Bebida(
    val drinks: List<Drink>
): Serializable {
    data class Drink(
        val idDrink: String,
        val strCategory: String,
        val strDrink: String,
        val strDrinkThumb: String) : Serializable
}