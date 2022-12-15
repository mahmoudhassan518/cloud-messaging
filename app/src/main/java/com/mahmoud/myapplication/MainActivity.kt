package com.mahmoud.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mahmoud.cloudmessaging.presentation.model.NotificationNavigationParam
import com.mahmoud.cloudmessaging.presentation.model.NotificationNavigationParam.Companion.FCM_NAVIGATION_PARAM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.getParcelableExtra<NotificationNavigationParam>(FCM_NAVIGATION_PARAM)?.let {
            initViews(it)
        }
    }

    private fun initViews(it: NotificationNavigationParam): Unit = with(it) {
        findViewById<TextView>(R.id.tv_id).text = id
        findViewById<TextView>(R.id.tv_screen).text = screen
    }
}
