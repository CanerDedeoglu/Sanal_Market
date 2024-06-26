package com.canerdedeoglu.afinal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canerdedeoglu.afinal.adapter.CategoryAdapter
import com.canerdedeoglu.afinal.configs.ApiClient
import com.canerdedeoglu.afinal.databinding.FragmentCategoryBinding
import com.canerdedeoglu.afinal.models.Categories
import com.canerdedeoglu.afinal.services.IDummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var dummyService: IDummyService
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryList: List<Categories>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        // Setup the toolbar
        val toolbar = (requireActivity() as MainActivity).binding.toolbar
        toolbar.visibility = View.VISIBLE
        toolbar.title = "Kategoriler"
        toolbar.setTitleTextColor(resources.getColor(R.color.home, null))


        recyclerView = binding.categoryRecycleView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        dummyService = ApiClient.getClient().create(IDummyService::class.java)

        dummyService.getCategories().enqueue(object : Callback<List<Categories>> {
            override fun onResponse(call: Call<List<Categories>>, response: Response<List<Categories>>) {
                if (response.isSuccessful) {
                    categoryList = response.body() ?: emptyList()
                    categoryAdapter = CategoryAdapter(requireContext(), categoryList)
                    recyclerView.adapter = categoryAdapter
                    Log.d("Category Data", categoryList.toString())
                } else {
                    Log.e("CategoryFragment", "Response not successful")
                }
            }

            override fun onFailure(call: Call<List<Categories>>, t: Throwable) {
                Log.e("CategoryFragment", t.message ?: "Error")
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
