package com.adem.pokemonapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager


class CustomSharedPreferences  {

    companion object {
        private var sharedPreferences : SharedPreferences? = null

        private var instance : CustomSharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(lock) {
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }

    fun saveData(name: String, frontUrl: String, backUrl: String){
        sharedPreferences?.edit(commit = true) {
            this.putString("name", name)
            this.putString("frontUrl", frontUrl)
            this.putString("backUrl", backUrl)
        }
    }

    fun getOverlayData(): List<String?> {

        val name = sharedPreferences?.getString("name", "null")
        val frontUrl = sharedPreferences?.getString("frontUrl", "null")
        val backUrl = sharedPreferences?.getString("backUrl", "null")

        return  listOf(name, frontUrl, backUrl)
    }

}