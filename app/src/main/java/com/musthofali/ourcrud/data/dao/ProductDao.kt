package com.musthofali.ourcrud.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.musthofali.ourcrud.data.entity.Product


@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM product WHERE pid IN (:productIds)")
    fun loadAllByIds(productIds: IntArray): List<Product>

    @Insert
    fun insertAll(vararg product: Product)

    @Delete
    fun delete(product: Product)

    @Query("SELECT * FROM product WHERE pid = :pid")
    fun get(pid: Int) : Product

    @Update
    fun update(product: Product)
}