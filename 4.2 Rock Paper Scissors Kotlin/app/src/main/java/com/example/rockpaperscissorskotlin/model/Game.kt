package com.example.rockpaperscissorskotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "game")
class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "result")
    var winner: Winner?,

    @ColumnInfo(name = "date")
    var date: Date = Date(),

    @ColumnInfo(name = "playerMove")
    var playerMove: Move,

    @ColumnInfo(name = "computerMove")
    var computerMove: Move

) {

    fun getResultText(): String = when(winner) {
        Winner.PLAYER -> "You won!"
        Winner.COMPUTER -> "Computer wins!"
        Winner.NO_WINNER -> "Draw"
        else -> "?"
    }

    fun determineWinner() : Winner = when(playerMove) {
        Move.ROCK -> {
            when (computerMove) {
                Move.ROCK -> Winner.NO_WINNER
                Move.PAPER -> Winner.COMPUTER
                Move.SCISSORS -> Winner.PLAYER
            }
        }
        Move.PAPER -> {
            when (computerMove) {
                Move.ROCK -> Winner.PLAYER
                Move.PAPER -> Winner.NO_WINNER
                Move.SCISSORS -> Winner.COMPUTER
            }
        }
        Move.SCISSORS ->
            when (computerMove) {
                Move.ROCK -> Winner.COMPUTER
                Move.PAPER -> Winner.PLAYER
                Move.SCISSORS -> Winner.NO_WINNER
            }
    }
}