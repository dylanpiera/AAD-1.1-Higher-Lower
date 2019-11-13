package com.dylanpiera.logicaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tfSwitch.setOnClickListener {
            OnUpdateSwitch(tfSwitch)
        }
        ttSwitch.setOnClickListener {
            OnUpdateSwitch(ttSwitch)
        }
        ftSwitch.setOnClickListener {
            OnUpdateSwitch(ftSwitch)
        }
        ffSwitch.setOnClickListener {
            OnUpdateSwitch(ffSwitch)
        }

        btnSubmit.setOnClickListener {
            OnSubmit()
        }
    }

    private fun OnSubmit() {
        if(ttSwitch.isChecked && !tfSwitch.isChecked && !ftSwitch.isChecked && !ffSwitch.isChecked)
            Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_LONG).show()
    }
    
    private fun OnUpdateSwitch(switch: Switch) {
        if(!switch.isChecked) {
            switch.text = getString(R.string.falsevaluefull)
        }
        else {
            switch.text = getString(R.string.truevaluefull)
        }
    }
}
