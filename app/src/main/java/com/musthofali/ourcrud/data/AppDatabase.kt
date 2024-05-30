package com.musthofali.ourcrud.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.musthofali.ourcrud.data.dao.OrderDao
import com.musthofali.ourcrud.data.dao.ProductDao
import com.musthofali.ourcrud.data.dao.UserDao
import com.musthofali.ourcrud.data.entity.Order
import com.musthofali.ourcrud.data.entity.Product
import com.musthofali.ourcrud.data.entity.User

@Database(entities = [User::class, Order::class, Product::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = databaseBuilder(context, AppDatabase::class.java, "app-database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return instance!!
        }
    }
}