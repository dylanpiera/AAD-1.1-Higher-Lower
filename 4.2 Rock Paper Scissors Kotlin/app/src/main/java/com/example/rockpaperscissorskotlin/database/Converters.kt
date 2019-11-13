package com.example.rockpaperscissorskotlin.database

import androidx.room.TypeConverter
import com.example.rockpaperscissorskotlin.model.Move
import com.example.rockpaperscissorskotlin.model.Winner
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun gestureToInt(gesture: Move): Int {
        return Move.values().indexOf(gesture)
    }

    @TypeConverter
    fun intToGesture(value: Int): Move {
        return Move.values()[value]
    }

    @TypeConverter
    fun winnerToInt(winner: Winner) : Int {
        return Winner.values().indexOf(winner)
    }

    @TypeConverter
    fun intToWinner(value: Int): Winner {
        return Winner.values()[value]
    }
}