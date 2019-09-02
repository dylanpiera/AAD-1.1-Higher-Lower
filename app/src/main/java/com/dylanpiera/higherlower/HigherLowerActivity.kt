package com.dylanpiera.higherlower

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_higher_lower.*

class HigherLowerActivity : AppCompatActivity() {

    private var currentThrow: Int = 1
    private var lastThrow: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_higher_lower)

        btnEqual.setOnClickListener{onBtnClick(DiceOption.EQUAL)}
        btnHigher.setOnClickListener{onBtnClick(DiceOption.HIGHER)}
        btnLower.setOnClickListener{onBtnClick(DiceOption.LOWER)}

        initViews()
    }

    private fun rollDice() {
        lastThrow = currentThrow
        currentThrow = (1..6).random()
        updateUI()
    }

    enum class DiceOption {
        LOWER, EQUAL, HIGHER
    }

    private fun onBtnClick(option: DiceOption) {
        rollDice()
        val success = when(option) {
            DiceOption.LOWER -> lastThrow > currentThrow
            DiceOption.EQUAL -> lastThrow == currentThrow
            DiceOption.HIGHER -> lastThrow < currentThrow
        }

        if(success)
            Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_LONG).show()
    }

    private fun updateUI() {
        when(currentThrow) {
            1 -> diceResult.setImageResource(R.drawable.dice1)
            2 -> diceResult.setImageResource(R.drawable.dice2)
            3 -> diceResult.setImageResource(R.drawable.dice3)
            4 -> diceResult.setImageResource(R.drawable.dice4)
            5 -> diceResult.setImageResource(R.drawable.dice5)
            6 -> diceResult.setImageResource(R.drawable.dice6)
            else -> {
                Toast.makeText(this,    "An Unexpected Error Occurred.", Toast.LENGTH_LONG).show()
            }
        }
        diceHelpText.text = getString(R.string.dicehelptext, lastThrow)
    }

    private fun initViews() {
        updateUI()
    }
}
