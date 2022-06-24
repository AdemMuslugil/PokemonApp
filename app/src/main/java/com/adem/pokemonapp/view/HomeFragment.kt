package com.adem.pokemonapp.view

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adem.pokemonapp.databinding.FragmentHomeBinding
import com.adem.pokemonapp.adapter.PokemonAdapter
import com.adem.pokemonapp.model.Result
import com.adem.pokemonapp.viewmodel.HomeViewModel


@Suppress("UNCHECKED_CAST")
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private val pokemonAdapter = PokemonAdapter(arrayListOf())

    //page offset
    private var offset : String = "0"

    var page = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getDataFromAPI(offset)

        //pokemonList -> RecyclerView
        binding.pokemonList.layoutManager = LinearLayoutManager(context)
        binding.pokemonList.adapter = pokemonAdapter

        binding.pageTextView.text = page.toString()

        observeLiveData()
        swipeRefresh(offset)
        changePage()
        tryAgain()

    }


    fun changePage(){
        //go to next page
        binding.nextPageButton.setOnClickListener {

            if (page>=1){
                page++
                offset = (offset.toInt()+20).toString()
                println(offset)
                viewModel.getDataFromAPI(offset)
                binding.pageTextView.text = page.toString()
            }
        }

        //go to previous page
        binding.previousPageButton.setOnClickListener {
           if(page>1){
                page--

                    offset = (offset.toInt()-20).toString()
                    println(offset)
                    viewModel.getDataFromAPI(offset)
                    binding.pageTextView.text = page.toString()
                }
           else{
               Toast.makeText(context, "First Page!", Toast.LENGTH_SHORT).show()
           }

        }


    }

    private fun observeLiveData(){

        viewModel.pokemonsLiveData.observe(viewLifecycleOwner, Observer {

            it?.let {
                binding.pokemonList.visibility = View.VISIBLE
                pokemonAdapter.updatePokemonList(it.results!! as List<Result>)
                viewModel.pokeLoading.value = false
                viewModel.pokeError.value = false
            }
        })

        viewModel.pokeError.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.pokemonList.visibility = View.GONE
                binding.homeLinearLayout.visibility = View.VISIBLE
                binding.pokeLoading.visibility = View.GONE
            }
            else{
                binding.homeLinearLayout.visibility = View.GONE
            }
        })

        viewModel.pokeLoading.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.pokemonList.visibility = View.GONE
                binding.pokeLoading.visibility = View.VISIBLE
                binding.homeLinearLayout.visibility = View.GONE
            }
            else{
                binding.pokeLoading.visibility = View.GONE
            }
        })

    }

    fun swipeRefresh(offset: String){
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.pokeLoading.visibility = View.VISIBLE
            viewModel.getDataFromAPI(offset)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    fun tryAgain(){ //Try Again Button
        binding.tryAgainButton.setOnClickListener {
            viewModel.getDataFromAPI(offset)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}