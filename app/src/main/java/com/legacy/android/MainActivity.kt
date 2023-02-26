/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.legacy.android.ui.NavDrawerActivity

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.yellowForSplash)
        }


        tobAnim = AnimationUtils.loadAnimation(this, R.anim.tob_anim)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim)

        image = findViewById(R.id.logo)
        slogan = findViewById(R.id.slogan1)

        image?.animation = tobAnim
        slogan?.animation = bottomAnim

        mHandler.postDelayed({
            val intent = Intent(this@MainActivity, NavDrawerActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}