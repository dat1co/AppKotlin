package com.example.myapplication


data class PokemonResponse(
    val name: String,
    val sprites: Sprites,
    val height: Int,
    val types: List<Type>,
    val weight: Int
)

data class Sprites(
    val front_default: String
)
data class Type(
    val name: String
)