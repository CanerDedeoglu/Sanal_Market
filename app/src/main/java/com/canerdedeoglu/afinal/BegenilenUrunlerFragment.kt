package com.canerdedeoglu.afinal

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.canerdedeoglu.afinal.MainActivity
import com.canerdedeoglu.afinal.R
import com.canerdedeoglu.afinal.adapter.FavoriteAdapter
import com.canerdedeoglu.afinal.databinding.FragmentBegenilenUrunlerBinding
import com.canerdedeoglu.afinal.models.ProductViewModel

class BegenilenUrunlerFragment : Fragment() {

    private var _binding: FragmentBegenilenUrunlerBinding? = null
    private val binding get() = _binding!!

    private lateinit var productViewModel: ProductViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBegenilenUrunlerBinding.inflate(inflater, container, false)

        // ViewModel'ı init et
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        // Toolbar title and style settings
        val toolbar = (requireActivity() as MainActivity).binding.toolbar
        toolbar.visibility = View.VISIBLE
        toolbar.title = "Beğenilen Ürünler"
        toolbar.setTitleTextColor(resources.getColor(R.color.home, null))

        // RecyclerView setup for favorite products
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(requireContext())
        favoriteAdapter = FavoriteAdapter(requireContext(), emptyList())
        binding.recyclerViewFavorites.adapter = favoriteAdapter

        // Load favorite products from Room database
        loadFavorites()

        // Clear button click listener
        binding.btnClear.setOnClickListener {
            clearFavorites()
        }

        return binding.root
    }

    private fun loadFavorites() {
        val userId = getUserIdFromPreferences(requireContext())
        userId?.let { uid ->
            productViewModel.getFavoritesByUserId(uid).observe(viewLifecycleOwner, { favorites ->
                if (favorites.isNotEmpty()) {
                    favoriteAdapter.updateFavorites(favorites)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Beğenilen ürün bulunmuyor.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } ?: run {
            Toast.makeText(requireContext(), "Kullanıcı ID bulunamadı.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFavorites() {
        productViewModel.deleteAllFavorites()
        favoriteAdapter.updateFavorites(emptyList())
        Toast.makeText(requireContext(), "Beğenilen ürünler temizlendi!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getUserIdFromPreferences(context: Context): Int? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("user_id", null)?.toIntOrNull()
    }
}
