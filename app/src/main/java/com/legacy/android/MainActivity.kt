package com.legacy.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : Activity() {

    //VARIABLES
    var tobAnim: Animation? = null
    var bottomAnim: Animation? = null
    var image: ImageView? = null
    var slogan: TextView? = null

    var mHandler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tobAnim = AnimationUtils.loadAnimation(this, R.anim.tob_anim)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim)

        image = findViewById(R.id.logo)
        slogan = findViewById(R.id.slogan1)

        image?.animation = tobAnim
        slogan?.animation = bottomAnim

        mHandler.postDelayed({
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}