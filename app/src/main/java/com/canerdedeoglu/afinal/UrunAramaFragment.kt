package com.canerdedeoglu.afinal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.canerdedeoglu.afinal.configs.ApiClient
import com.canerdedeoglu.afinal.databinding.FragmentUrunAramaBinding
import com.canerdedeoglu.afinal.models.Products
import com.canerdedeoglu.afinal.services.IDummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class UrunAramaFragment : Fragment() {

    private var _binding: FragmentUrunAramaBinding? = null
    private val binding get() = _binding!!
    private lateinit var dummyService: IDummyService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUrunAramaBinding.inflate(inflater, container, false)

        val toolbar = (activity as MainActivity).binding.toolbar
        toolbar.title = "Ürün Arama"
        toolbar.setTitleTextColor(resources.getColor(R.color.home))
        //toolbar.inflateMenu(R.menu.toolbar_menu)

        // setHasOptionsMenu(true) // Bu fragment'in menüsü olduğunu belirtmek için

        binding.btnAra.setOnClickListener {
            urunAra()
        }
        binding.icDelete.setOnClickListener {
            binding.txtUrunAd.setText("")
            binding.txtUrunFiyat.setText("")
            binding.txtCategory.setText("")
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup AutoCompleteTextView
        val categories = resources.getStringArray(R.array.Categories)
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, categories)
        binding.txtCategory.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun urunAra() {
        val urunAd = binding.txtUrunAd.text.toString().trim()
        val titleUrunAd = binding.titleUrunAd
        val urunFiyat = binding.txtUrunFiyat.text.toString().trim()
        val titleUrunFiyat = binding.titleUrunFiyat
        val urunKategori = binding.txtCategory.text.toString().trim()
        val titleUrunKategori = binding.titleCategory
        var isValid = false

        // Alanlardan en az birinin dolu olup olmadığını kontrol ediyoruz
        if (urunAd.isNotEmpty() || urunFiyat.isNotEmpty() || urunKategori.isNotEmpty()) {
            isValid = true
        }

        // Hata mesajlarını temizliyoruz
        titleUrunAd.isErrorEnabled = false
        titleUrunFiyat.isErrorEnabled = false
        titleUrunKategori.isErrorEnabled = false

        if (isValid) {
            // Ürün arama işlemleri için URL oluşturma
            var queryUrl = "https://dummyjson.com/products/"

            // Arama kriterlerini ekle
            if (urunKategori.isNotEmpty()) {
                val formattedCategory = urunKategori.replace("\\s+".toRegex(), "-")
                queryUrl += "category/$formattedCategory"
            } else {
                Log.d("urunArama", "Kategori bilgisi girilmediği için tüm ürünler aranacak")
            }

            // Arama isteği yapma
            dummyService = ApiClient.getClient().create(IDummyService::class.java)
            dummyService.searchProducts(queryUrl).enqueue(object : Callback<Products> {
                override fun onResponse(call: Call<Products>, response: Response<Products>) {
                    if (response.isSuccessful) {
                        val allProducts = response.body()?.products ?: emptyList()

                        // İlk filtreleme: ürün adı ile eşleşen ürünleri bul
                        val filteredProducts = allProducts.filter { product ->
                            var matches = true
                            if (urunAd.isNotEmpty()) {
                                matches = matches && product.title.contains(urunAd, ignoreCase = true)
                            }
                            if (urunFiyat.isNotEmpty()) {
                                matches = matches && product.price.toString() == urunFiyat
                            }
                            matches
                        }

                        if (filteredProducts.isEmpty()) {
                            Log.d("urunArama", "Aranan ürün bulunamadı")
                            // Kullanıcıya mesaj göster
                            showToast("Aranan kriterlere uygun ürün bulunamadı.")
                        } else {
                            // Filtrelenmiş ürünleri göster
                            Log.d("urunArama", "$filteredProducts")

                            // Ürün arama işlemi başarılı oldu
                            // Başka bir fragmenta geçiş yapma
                            val action = UrunAramaFragmentDirections.actionToSearch(Products(filteredProducts, 0, 0, filteredProducts.size.toLong()))
                            findNavController().navigate(action)
                        }
                    } else {
                        Log.e("urunArama", "Response error: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<Products>, t: Throwable) {
                    // Hata durumu
                    Log.e("urunArama", "Ürün arama işlemi başarısız oldu", t)
                }
            })
        } else {
            // Eğer hiçbir alan doldurulmadıysa hata mesajlarını gösteriyoruz
            if (urunAd.isEmpty()) {
                titleUrunAd.error = "Ad giriniz"
                titleUrunAd.isErrorEnabled = true
            }
            if (urunFiyat.isEmpty()) {
                titleUrunFiyat.error = "Fiyat giriniz"
                titleUrunFiyat.isErrorEnabled = true
            }
            if (urunKategori.isEmpty()) {
                titleUrunKategori.error = "Kategori giriniz"
                titleUrunKategori.isErrorEnabled = true
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
