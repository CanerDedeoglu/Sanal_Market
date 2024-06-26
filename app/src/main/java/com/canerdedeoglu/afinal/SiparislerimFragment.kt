package com.canerdedeoglu.afinal


import OrderAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canerdedeoglu.afinal.configs.ApiClient
import com.canerdedeoglu.afinal.databinding.FragmentSiparislerimBinding
import com.canerdedeoglu.afinal.models.UserOrdersResponse
import com.canerdedeoglu.afinal.services.IDummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SiparislerimFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_siparislerim, container, false)

        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        toolbar?.title = "Sipariş Detayı"
        toolbar?.setTitleTextColor(resources.getColor(R.color.home))
        //toolbar.inflateMenu(R.menu.toolbar_menu)

        // setHasOptionsMenu(true) // Bu fragment'in menüsü olduğunu belirtmek için

        recyclerView = view.findViewById(R.id.recyclerViewOrders)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Sadece userId'si 6 olan kullanıcının siparişlerini al ve RecyclerView'e bağla
        fetchOrdersForUser(6)

        return view
    }

    private fun fetchOrdersForUser(userId: Int) {
        val RetrofitClient = ApiClient.getClient().create(IDummyService::class.java)
        RetrofitClient.getUserOrders(userId).enqueue(object : Callback<UserOrdersResponse> {
            override fun onResponse(call: Call<UserOrdersResponse>, response: Response<UserOrdersResponse>) {
                if (response.isSuccessful) {
                    val userOrdersResponse = response.body()
                    val orders = userOrdersResponse?.carts ?: emptyList()
                    orderAdapter = OrderAdapter(orders)
                    recyclerView.adapter = orderAdapter
                } else {
                    // Handle error
                    Log.e("SiparislerimFragment", "API'den siparişler alınamadı: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserOrdersResponse>, t: Throwable) {
                // Handle failure
                Log.e("SiparislerimFragment", "API ile iletişim hatası", t)
            }
        })
    }
}
