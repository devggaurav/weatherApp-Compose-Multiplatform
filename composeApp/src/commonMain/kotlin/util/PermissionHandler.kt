package util


//
// Created by Code For Android on 17/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

interface PermissionHandler {

    fun requestLocationPermission(
        permission: List<String>,
        onPermissionResult: (Map<String, Boolean>) -> Unit
    )

    fun arePermissionGranted(permission: List<String>): Boolean
}