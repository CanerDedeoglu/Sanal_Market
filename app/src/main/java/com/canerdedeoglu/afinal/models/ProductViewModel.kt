package com.canerdedeoglu.afinal.models

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository
    val allFavorites: LiveData<List<Favorite>> // Favori ürünler için LiveData

    init {
        val productDao = ProductDatabase.getDatabase(application).favoriteDao()
        repository = ProductRepository(productDao)
        val userId = getUserIdFromPreferences(application) ?: -1
        allFavorites = repository.getFavoritesByUserId(userId)
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.insertFavorite(favorite)
    }

    fun deleteAllFavorites() = viewModelScope.launch {
        repository.deleteAllFavorites()
    }

    fun getFavoritesByUserId(userId: Int): LiveData<List<Favorite>> {
        return repository.getFavoritesByUserId(userId)
    }

    private fun getUserIdFromPreferences(context: Context): Int? {
        val sharedPreferences =
            context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("user_id", null)?.toIntOrNull()
    }
}
