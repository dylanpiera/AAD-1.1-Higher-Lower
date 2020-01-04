package com.soaringnetwork.debtchecker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_activity.*
import okhttp3.MediaType
import okhttp3.OkHttpClient



class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        loginWithDiscord.setOnClickListener{
            LoginWithDiscord()
        }
    }

    private fun LoginWithDiscord() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent);
    }
}
