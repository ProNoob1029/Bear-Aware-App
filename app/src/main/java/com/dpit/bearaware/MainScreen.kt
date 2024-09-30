package com.dpit.bearaware

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.longdo.mjpegviewer.MjpegView

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    MjpegView(context).apply {
                        mode = MjpegView.MODE_FIT_WIDTH
                        isAdjustHeight = true
                        supportPinchZoomAndPan = true
                        setUrl("http://192.168.100.99:8080/camera_output")
                        startStream()
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}