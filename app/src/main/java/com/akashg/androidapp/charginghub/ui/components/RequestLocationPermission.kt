package com.akashg.androidapp.charginghub.ui.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.akashg.androidapp.charginghub.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    requestPermission: Boolean = false,
    clearPermission: () -> Unit,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    val prefs = context.getSharedPreferences(
        stringResource(R.string.local_db_name),
        Context.MODE_PRIVATE,
    )
    val askedLocationKey = stringResource(R.string.already_permission_requested)
    var firstRequestDone by remember {
        mutableStateOf(prefs.getBoolean(askedLocationKey, false))
    }

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    )

    val anyPermissionPermanentlyDenied = firstRequestDone &&
        permissionsState.permissions.any { perm ->
            !perm.status.isGranted && !permissionsState.shouldShowRationale
        }
    var showDialog by remember(requestPermission) { mutableStateOf(!permissionsState.allPermissionsGranted) }

    LaunchedEffect(Unit) {
        if (!permissionsState.shouldShowRationale) {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

    when {
        anyPermissionPermanentlyDenied -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(stringResource(R.string.open_settings_description))
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text(stringResource(R.string.open_settings))
                }
            }
        }

        else -> {
            content()
            if (requestPermission && showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                        clearPermission()
                    },
                    title = { Text(stringResource(R.string.permission_required_title)) },
                    text = { Text(stringResource(R.string.permission_description)) },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                            firstRequestDone = true
                            prefs.edit { putBoolean(askedLocationKey, true) }
                            permissionsState.launchMultiplePermissionRequest()
                            clearPermission()
                        }) {
                            Text(stringResource(R.string.grant_permission_text))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                            clearPermission()
                        }) {
                            Text(stringResource(R.string.dialog_dismiss_button))
                        }
                    },
                )
            }
        }
    }
}
