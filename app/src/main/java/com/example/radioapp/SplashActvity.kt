package com.example.radioapp
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashActvity : AppCompatActivity() {


    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_actvity)


        handler = Handler()
        handler!!.postDelayed({
            val intent = Intent(this@SplashActvity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)


    }
}