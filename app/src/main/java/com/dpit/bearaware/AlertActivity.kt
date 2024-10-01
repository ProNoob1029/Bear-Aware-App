package com.dpit.bearaware

import android.app.KeyguardManager
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.dpit.bearaware.ui.theme.BearAwareTheme

class AlertActivity : ComponentActivity() {
    private val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            BearAwareTheme(
                darkTheme = false
            ) {
                Scaffold { scaffoldPadding ->
                    Column (
                        modifier = Modifier
                            .padding(scaffoldPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "BEAR DETECTED",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Black
                            )
                        )
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(R.drawable.bear_icon),
                            contentDescription = "bear"
                        )
                    }
                }
            }
        }

        manageLockScreen()

        setupMediaPlayer()
    }

    override fun onStop() {
        super.onStop()
        println("STOPPED")
        mediaPlayer.stop()
        finish()
    }

    private fun setupMediaPlayer() {
        val uri = Uri.parse("android.resource://com.dpit.bearaware/${R.raw.alarm_sound}")

        mediaPlayer.setDataSource(applicationContext, uri)

        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
        )

        mediaPlayer.prepare()

        mediaPlayer.start()

        mediaPlayer.isLooping = true
    }

    private fun manageLockScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)
        {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }
    }
}