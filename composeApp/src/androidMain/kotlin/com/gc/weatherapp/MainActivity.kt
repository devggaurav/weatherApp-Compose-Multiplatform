package com.gc.weatherapp

import App
import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.context.KoinContext
import org.koin.java.KoinJavaComponent.inject
import presentation.screen.HomeScreenViewModel
import util.LocationProvider

class MainActivity : ComponentActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private val viewModel: HomeScreenViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {

          viewModel.checkPermission()
        }

        lifecycleScope.launch {
            val permission = LocationProvider(this@MainActivity).requestLocationPermission()

            if (!permission) {

                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION

                    )
                )

            }

        }

        setContent {
            App()
        }


    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}