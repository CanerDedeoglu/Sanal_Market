package com.canerdedeoglu.afinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.canerdedeoglu.afinal.adapter.ProductAdapter
import com.canerdedeoglu.afinal.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val toolbar = (activity as MainActivity).binding.toolbar
        toolbar.title = "Ürün Listesi"
        toolbar.setTitleTextColor(resources.getColor(R.color.home))
        //toolbar.inflateMenu(R.menu.toolbar_menu)

        // setHasOptionsMenu(true) // Bu fragment'in menüsü olduğunu belirtmek için
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filteredProducts = args.Products.products

        // RecyclerView ve Adapter kurulumu
        val adapter = ProductAdapter(requireContext(), filteredProducts)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
