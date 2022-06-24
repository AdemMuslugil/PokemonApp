package com.adem.pokemonapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.adem.pokemonapp.model.DetailsModel
import com.adem.pokemonapp.util.CustomSharedPreferences
import com.adem.pokemonapp.api.PokeAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(application: Application)  : BaseViewModel(application){

    private val pokeAPIService = PokeAPIService()
    private var disposables = CompositeDisposable()
    private var customSharedPreferences = CustomSharedPreferences(getApplication())

    val detailsPokemon = MutableLiveData<DetailsModel>()
    val detailsLoading = MutableLiveData<Boolean>()
    val detailsError = MutableLiveData<Boolean>()


    fun getPokemonDetails(name : String){

        detailsLoading.value = true

        disposables.add(
            pokeAPIService.getDetails(name)
                .subscribeOn(Schedulers.newThread()) //in newThread to download
                .observeOn(AndroidSchedulers.mainThread()) //in mainThread to observe
                .subscribeWith(object : DisposableSingleObserver<DetailsModel>(){
                    override fun onSuccess(t: DetailsModel) {
                        detailsPokemon.value = t

                        // save data to SharedPreferences
                        with(customSharedPreferences) { saveData(t.name!!,t.sprites!!.frontDefault!!,t.sprites.backDefault!!) }
                    }

                    override fun onError(e: Throwable) {
                        println("error "+e.message)
                        detailsError.value = true
                        detailsLoading.value = false
                    }

                }))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}