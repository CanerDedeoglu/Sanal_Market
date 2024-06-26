package com.canerdedeoglu.afinal.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_products")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Insert
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE FROM favorite_products")
    suspend fun deleteAllFavorites()

    @Query("SELECT * FROM favorite_products WHERE userId = :userId")
    fun getFavoritesByUserId(userId: Int): LiveData<List<Favorite>>
}
