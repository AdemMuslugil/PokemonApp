package com.adem.pokemonapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adem.pokemonapp.api.PokeAPIService
import com.adem.pokemonapp.model.MainModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private val pokeAPIService = PokeAPIService()
    private val disposable = CompositeDisposable()


    val pokemonsLiveData = MutableLiveData<MainModel>()
    val pokeError = MutableLiveData<Boolean> ()
    val pokeLoading = MutableLiveData<Boolean>()


    fun getDataFromAPI(offset: String){

        pokeLoading.value = true

        disposable.add(
            pokeAPIService.getData(offset)
                .subscribeOn(Schedulers.newThread()) //in newThread to download
                .observeOn(AndroidSchedulers.mainThread()) //in mainThread to observe
                .subscribeWith(object : DisposableSingleObserver<MainModel>(){
                    override fun onSuccess(t: MainModel) {
                        pokemonsLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                        println("Error: "+e.message)
                        pokeError.value = true
                        pokeLoading.value = false
                    }


                })
        )

    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}