package com.example.studentportalapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create.*
import java.net.URI

class CreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        btnAdd.setOnClickListener { addPortal() }
    }

    private fun addPortal() {
        if (etTitle.text.toString().isNotBlank() && etUrl.text.toString().isNotBlank()) {
            val portal = Portal(etTitle.text.toString(), URI(etUrl.text.toString()))

            val intent = Intent()
            intent.putExtra(EXTRA_PORTAL, portal)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, "Please make sure both fields are filled out", Toast.LENGTH_SHORT).show()
        }
    }
}
