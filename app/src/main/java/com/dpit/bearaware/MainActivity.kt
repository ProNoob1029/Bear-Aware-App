package com.dpit.bearaware

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.dpit.bearaware.ui.theme.BearAwareTheme
import android.Manifest
import android.content.Intent
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BearAwareTheme {
                MainScreen()
            }
        }

        if (Settings.canDrawOverlays(this).not()) {
            val intent = Intent()
                .setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)

            startActivity(intent)
        }

        askNotificationPermission(Manifest.permission.POST_NOTIFICATIONS)

        Firebase.messaging.subscribeToTopic("bear-alert")
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            println("Permission granted")
            // FCM SDK (and your app) can post notifications.
        } else {
            println("Permission denied")
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission(permission: String) {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(permission)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(permission)
            }
        }
    }
}
