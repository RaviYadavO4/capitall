package com.portfolio.capitall.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class CapitallDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: CapitallDatabase? = null

        fun getDatabase(context: Context): CapitallDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CapitallDatabase::class.java,
                    "capitall_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
