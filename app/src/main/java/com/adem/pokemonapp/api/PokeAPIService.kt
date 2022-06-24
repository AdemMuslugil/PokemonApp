package com.adem.pokemonapp.api

import com.adem.pokemonapp.api.PokeAPI
import com.adem.pokemonapp.model.DetailsModel
import com.adem.pokemonapp.model.MainModel
import com.adem.pokemonapp.util.Util.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class PokeAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(PokeAPI::class.java)


    fun getData(offset:String) : Single<MainModel>{
        return api.getData(offset)
    }


    fun getDetails(name : String): Single<DetailsModel>{
        return api.getDetails(name)
    }
}

