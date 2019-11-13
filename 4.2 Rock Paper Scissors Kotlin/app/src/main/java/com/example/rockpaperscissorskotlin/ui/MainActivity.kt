package com.example.rockpaperscissorskotlin.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.rockpaperscissorskotlin.R
import com.example.rockpaperscissorskotlin.database.GameRepository
import com.example.rockpaperscissorskotlin.model.Game
import com.example.rockpaperscissorskotlin.model.Move

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.game_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1
    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        gameRepository = GameRepository(this)

        ibRock.setOnClickListener { makeMove(Move.ROCK) }
        ibPaper.setOnClickListener { makeMove(Move.PAPER) }
        ibScissors.setOnClickListener { makeMove(Move.SCISSORS) }
    }

    private fun makeMove(playerMove: Move) {
        val game = startGame(playerMove)
        updateUI(game)
        saveGame(game)
    }

    private fun updateUI(game: Game) {
        LayoutInflater.from(this).inflate(R.layout.game_item, clParent, true)
        ivPlayerChoice.setImageDrawable(getDrawable(game.playerMove.image))
        ivComputerChoice.setImageDrawable(getDrawable(game.computerMove.image))
        tvResult.text = game.getResultText()
    }

    private fun startGame(playerMove: Move) : Game {
        val computerMove = Move.values()[(0..2).random()]
        val game = Game(playerMove = playerMove, computerMove = computerMove, winner = null)
        game.winner = game.determineWinner()
        return game
    }

    private fun saveGame(game: Game) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
    }

    private fun showHistoryPage() {
        val intent = Intent(this, GameHistoryActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                showHistoryPage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
