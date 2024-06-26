package com.canerdedeoglu.afinal.models

import androidx.lifecycle.LiveData


class ProductRepository(private val productDao: FavoriteDao) {

    suspend fun insertFavorite(favorite: Favorite) {
        productDao.insertFavorite(favorite)
    }
    fun getFavoritesByUserId(userId: Int): LiveData<List<Favorite>> {
        return productDao.getFavoritesByUserId(userId)
    }
    suspend fun deleteAllFavorites() {
        productDao.deleteAllFavorites()
    }

}
