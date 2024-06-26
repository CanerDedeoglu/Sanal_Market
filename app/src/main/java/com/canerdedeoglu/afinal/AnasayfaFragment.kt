package com.canerdedeoglu.afinal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canerdedeoglu.afinal.adapter.ProductAdapter
import com.canerdedeoglu.afinal.configs.ApiClient
import com.canerdedeoglu.afinal.databinding.FragmentAnasayfaBinding
import com.canerdedeoglu.afinal.models.Product
import com.canerdedeoglu.afinal.models.Products
import com.canerdedeoglu.afinal.services.IDummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnasayfaFragment : Fragment() {

    private lateinit var binding: FragmentAnasayfaBinding
    private lateinit var dummyService: IDummyService
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: List<Product>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnasayfaBinding.inflate(inflater, container, false)
        val toolbar = (activity as MainActivity).binding.toolbar
        toolbar.title = "Anasayfa"
        toolbar.setTitleTextColor(resources.getColor(R.color.home))

        // Enable the back button in the toolbar
        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        recyclerView = binding.recycleview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        dummyService = ApiClient.getClient().create(IDummyService::class.java)
        dummyService.getProducts().enqueue(object : Callback<Products> {
            override fun onResponse(p0: Call<Products>, p1: Response<Products>) {
                if (p1.isSuccessful) {
                    productList = p1.body()?.products ?: emptyList()
                    productAdapter = ProductAdapter(requireContext(), productList)
                    recyclerView.adapter = productAdapter
                    Log.d("datas", productList.toString())
                }
            }

            override fun onFailure(p0: Call<Products>, p1: Throwable) {
                Log.e("getProducts", p1.message ?: "Error")
            }
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        mainActivity.binding.toolbar.visibility = View.VISIBLE // Toolbar'ı görünür yap
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
