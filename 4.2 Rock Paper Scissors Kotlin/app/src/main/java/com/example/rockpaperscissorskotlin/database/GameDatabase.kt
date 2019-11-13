package com.example.rockpaperscissorskotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rockpaperscissorskotlin.model.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAME_DATABASE"

        @Volatile
        private var reminderRoomDatabaseInstance: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase? {
            if (reminderRoomDatabaseInstance == null) {
                synchronized(GameDatabase::class.java) {
                    if (reminderRoomDatabaseInstance == null) {
                        reminderRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            GameDatabase::class.java, DATABASE_NAME
                        ).build()
                    }
                }
            }
            return reminderRoomDatabaseInstance
        }
    }

}