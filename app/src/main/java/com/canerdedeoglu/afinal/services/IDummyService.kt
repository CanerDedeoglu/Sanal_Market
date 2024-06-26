package com.canerdedeoglu.afinal.services

import com.canerdedeoglu.afinal.models.Categories
import com.canerdedeoglu.afinal.models.LoginRequest
import com.canerdedeoglu.afinal.models.LoginResponse
import com.canerdedeoglu.afinal.models.Order
import com.canerdedeoglu.afinal.models.Products
import com.canerdedeoglu.afinal.models.User
import com.canerdedeoglu.afinal.models.UserOrdersResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

import retrofit2.http.Url

interface IDummyService {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
    @GET("/products")
    fun getProducts(): Call<Products>
    @GET("products/categories")
    fun getCategories(): Call<List<Categories>>
    @GET
    fun searchProducts(@Url url: String): Call<Products>
    @GET("/users/{id}")
    suspend fun getUserProfile(@Path("id") id: Int): User
    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): Products
    // Sepete ürün eklemek için POST isteği
    @POST("carts/add")
    fun addToCart(@Body cartItem: Map<String, String>): Call<Any>
    // Kullanıcının siparişlerini almak için GET isteği
    @GET("carts/user/{userId}")
    fun getUserOrders(@Path("userId") userId: Int): Call<UserOrdersResponse>

}
