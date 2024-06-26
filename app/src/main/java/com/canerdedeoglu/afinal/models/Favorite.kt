package com.canerdedeoglu.afinal.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_products")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Int, // Kullanıcı ID'si
    val productId: Long,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val stock: Long,
    val image: String,
    val reviewJson: String
):Serializable