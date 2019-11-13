package com.example.rockpaperscissorskotlin.model

import com.example.rockpaperscissorskotlin.R

enum class Move(val image: Int) {
    ROCK(R.drawable.rock),
    PAPER(R.drawable.paper),
    SCISSORS(R.drawable.scissors)
}