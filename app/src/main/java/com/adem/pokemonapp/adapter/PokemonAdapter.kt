package com.adem.pokemonapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.adem.pokemonapp.databinding.RecyclerRowBinding
import com.adem.pokemonapp.model.Result
import com.adem.pokemonapp.view.HomeFragmentDirections

class PokemonAdapter(private val pokeList : ArrayList<Result>) : RecyclerView.Adapter<PokemonAdapter.PokeHolder>() {
    class PokeHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PokeHolder(binding)
    }

    override fun onBindViewHolder(holder: PokeHolder, position: Int) {

        holder.binding.pokeNameTextView.text = pokeList[position].name


        //navigate to Details Fragment
        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(pokeList[position].name!!)
            Navigation.findNavController(it).navigate(action)
        }

    }

    fun updatePokemonList(newList:List<Result>){
        pokeList.clear()
        pokeList.addAll(newList)
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return pokeList.size
    }
}