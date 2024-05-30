package com.musthofali.ourcrud.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.musthofali.ourcrud.data.entity.Order


@Dao
interface OrderDao {
    @Query("SELECT * FROM `order` ")
    fun getAll(): List<Order>

    @Query("SELECT * FROM `order` WHERE oid IN (:orderIds)")
    fun loadAllByIds(orderIds: IntArray): List<Order>

    @Insert
    fun insertAll(vararg order: Order)

    @Delete
    fun delete(order: Order)

    @Query("SELECT * FROM `order` WHERE oid = :oid")
    fun get(oid: Int): Order

    @Update
    fun update(order: Order)
}
