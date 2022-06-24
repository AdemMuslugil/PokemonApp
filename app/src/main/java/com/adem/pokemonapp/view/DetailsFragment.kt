package com.adem.pokemonapp.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adem.pokemonapp.service.OverlayService
import com.adem.pokemonapp.databinding.FragmentDetailsBinding
import com.adem.pokemonapp.util.downloadImage
import com.adem.pokemonapp.util.placeHolderProgressBar
import com.adem.pokemonapp.viewmodel.DetailsViewModel



class DetailsFragment : Fragment() {

    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var name : String = ""
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            name = DetailsFragmentArgs.fromBundle(it).name
            println(name)
        }

        viewModel=ViewModelProvider(this)[DetailsViewModel::class.java]
        viewModel.getPokemonDetails(name)

        observeLiveData()
        swipeRefresh()

        binding.startOverlayButton.text = "SHOW $name IN OVERLAY"

        binding.startOverlayButton.setOnClickListener {
            val service = Intent(context,OverlayService::class.java)
            context!!.stopService(service)
            service.action = Intent.ACTION_MAIN
            context!!.startService(service)
            activity!!.finish()
        }

        binding.detailsTryAgainButton.setOnClickListener {
            viewModel.getPokemonDetails(name)
        }

    }

    fun observeLiveData(){
        //observe live data

        viewModel.detailsPokemon.observe(viewLifecycleOwner, Observer {

            binding.detailsLinearLayout.visibility = View.VISIBLE
            binding.detailsErrorLinearLayout.visibility = View.GONE
            binding.pokemonLoading.visibility = View.GONE


            binding.pokemonNameText.text = "Pokemon Name: ${it.name}"
            binding.pokemonHeightText.text = "Pokemon Height: ${it.height.toString()}"
            binding.pokemonWeightText.text = "Pokemon Weight ${it.weight.toString()}"
            binding.imageView.downloadImage(it.sprites!!.frontDefault!!, placeHolderProgressBar(context!!))

        })

        viewModel.detailsError.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.detailsLinearLayout.visibility = View.GONE
                binding.detailsErrorLinearLayout.visibility = View.VISIBLE
                binding.pokemonLoading.visibility = View.GONE
            }
            else{
                binding.detailsErrorLinearLayout.visibility = View.GONE

            }
        })

        viewModel.detailsLoading.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.detailsLinearLayout.visibility = View.GONE
                binding.detailsErrorLinearLayout.visibility = View.GONE
                binding.pokemonLoading.visibility = View.VISIBLE
            }
            else{
                binding.pokemonLoading.visibility = View.GONE
            }

        })

    }


    fun swipeRefresh(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.pokemonLoading.visibility = View.VISIBLE
            viewModel.getPokemonDetails(name)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}