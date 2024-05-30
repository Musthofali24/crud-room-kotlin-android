    package com.musthofali.ourcrud.data.entity

    import androidx.room.ColumnInfo
    import androidx.room.Entity
    import androidx.room.PrimaryKey

    @Entity
    data class Product(
        @PrimaryKey(autoGenerate = true) var pid: Int? = null,
        @ColumnInfo(name = "product_name") var productName: String?,
        @ColumnInfo(name = "price") var price: String?,
        @ColumnInfo(name = "stock") var stock: String?,
        @ColumnInfo(name = "category") var category: String,
        @ColumnInfo(name = "rating") var rating: Int
    )