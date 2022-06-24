package com.adem.pokemonapp.api


import com.adem.pokemonapp.model.DetailsModel
import com.adem.pokemonapp.model.MainModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokeAPI {



    @GET("api/v2/pokemon?limit=20") //pokemon list
    fun getData(
        @Query("offset") offset: String = "0"
    ): Single<MainModel>



    @GET("api/v2/pokemon/{name}") //pokemon details
    fun getDetails(
        @Path("name") name : String
    ): Single<DetailsModel>
}