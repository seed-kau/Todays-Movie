package com.kau.movie_search

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        openActivity ()
    }

    private fun openActivity () {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
