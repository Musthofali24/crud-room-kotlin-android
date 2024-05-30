package com.musthofali.ourcrud.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Order(
    @PrimaryKey(autoGenerate = true) var oid: Int? = null,
    @ColumnInfo(name = "customer_name") var customerName: String?,
    @ColumnInfo(name = "total_order") var totalOrder: Int?,
    @ColumnInfo(name = "order_date") var orderDate: String?,
    @ColumnInfo(name = "status") var status: String?,
    @ColumnInfo(name = "note") var note: String?
)