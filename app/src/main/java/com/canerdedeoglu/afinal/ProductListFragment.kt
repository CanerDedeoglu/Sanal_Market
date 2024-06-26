package com.canerdedeoglu.afinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canerdedeoglu.afinal.adapter.ProductAdapter
import com.canerdedeoglu.afinal.configs.ApiClient
import com.canerdedeoglu.afinal.databinding.FragmentProductListBinding
import com.canerdedeoglu.afinal.services.IDummyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)

        val toolbar = (activity as MainActivity).binding.toolbar
        toolbar.title = "Ürün Listesi"
        toolbar.setTitleTextColor(resources.getColor(R.color.home))
        //toolbar.inflateMenu(R.menu.toolbar_menu)

        // setHasOptionsMenu(true) // Bu fragment'in menüsü olduğunu belirtmek için

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            categoryName = ProductListFragmentArgs.fromBundle(it).categoryName
        }

        setupRecyclerView()
        loadProducts()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(requireContext(), listOf()) // Başlangıçta boş liste ile adapter oluşturuluyor
        binding.productListFragment.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun loadProducts() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                categoryName?.let {
                    val formattedCategoryName = it.replace("\\s+".toRegex(), "-")
                    val productsResponse = ApiClient.getClient().create(IDummyService::class.java).getProductsByCategory(formattedCategoryName)
                    productAdapter.updateProduct(productsResponse.products)
                }
            } catch (e: Exception) {

            }
        }
    }
}
