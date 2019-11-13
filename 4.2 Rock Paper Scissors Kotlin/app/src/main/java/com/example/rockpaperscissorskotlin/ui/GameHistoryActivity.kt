package com.example.rockpaperscissorskotlin.ui

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissorskotlin.GameAdapter
import com.example.rockpaperscissorskotlin.R
import com.example.rockpaperscissorskotlin.database.GameRepository
import com.example.rockpaperscissorskotlin.model.Game
import kotlinx.android.synthetic.main.content_game_history.*
import kotlinx.android.synthetic.main.history_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameHistoryActivity : AppCompatActivity() {

    private val games = arrayListOf<Game>()
    private lateinit var gamesAdapter: GameAdapter
    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_main)
        setSupportActionBar(toolbar)

        gameRepository = GameRepository(this)

        // Add back button
        val actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Get screen height to ensmallen game items dynamically
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels

        gamesAdapter = GameAdapter(games, screenHeight)

        ibDelete.setOnClickListener { deleteGames() }

        initViews()
    }

    private fun initViews() {
        rvGames.adapter = gamesAdapter
        rvGames.layoutManager = LinearLayoutManager(this@GameHistoryActivity, RecyclerView.VERTICAL, false)
        rvGames.addItemDecoration(
            DividerItemDecoration(
                this@GameHistoryActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        getGames()
    }

    private fun getGames() {
        CoroutineScope(Dispatchers.Main).launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@GameHistoryActivity.games.clear()
            this@GameHistoryActivity.games.addAll(games)
            gamesAdapter.notifyDataSetChanged()
        }
    }

    private fun deleteGames() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGames()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}