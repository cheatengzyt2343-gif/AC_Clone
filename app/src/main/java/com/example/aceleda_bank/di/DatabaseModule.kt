package com.example.aceleda_bank.di

import android.content.Context
import androidx.room.Room
import com.example.aceleda_bank.DAO.AccountDao
import com.example.aceleda_bank.DAO.TransactionDao
import com.example.aceleda_bank.DAO.UserDao
import com.example.aceleda_bank.Database.AppDatabase
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bank_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(
        db: AppDatabase
    ): UserDao {
        return db.userDao()
    }

    @Provides
    fun provideAccountDao(
        db: AppDatabase
    ): AccountDao {
        return db.accountDao()
    }

    @Provides
    fun provideTransactionDao(
        db: AppDatabase
    ): TransactionDao {
        return db.transactionDao()
    }
}
