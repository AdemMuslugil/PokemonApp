package com.adem.pokemonapp.model


import com.google.gson.annotations.SerializedName

data class DetailsModel(
    @SerializedName("base_experience")
    val baseExperience: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("held_items")
    val heldİtems: List<Any>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_default")
    val isDefault: Boolean?,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("past_types")
    val pastTypes: List<Any>?,
    @SerializedName("sprites")
    val sprites: Sprites?,
    @SerializedName("weight")
    val weight: Int?
)