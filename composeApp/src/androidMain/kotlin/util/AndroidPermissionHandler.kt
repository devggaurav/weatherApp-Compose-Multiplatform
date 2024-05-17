package util

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable


//
// Created by Code For Android on 17/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

class AndroidPermissionHandler(private val activity: Context) : PermissionHandler {


    @Composable
    fun requestPermissionsInCompose(permissions: List<String>, onPermissionsResult: (Map<String, Boolean>) -> Unit) {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            onPermissionsResult(result)
        }
        launcher.launch(permissions.toTypedArray())
    }

    override fun requestLocationPermission(
        permission: List<String>,
        onPermissionResult: (Map<String, Boolean>) -> Unit
    ) {
        TODO("Not yet implemented")
    }


    override fun arePermissionGranted(permission: List<String>): Boolean {
        TODO("Not yet implemented")
    }
}