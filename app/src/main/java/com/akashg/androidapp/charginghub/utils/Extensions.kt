package com.akashg.androidapp.charginghub.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.LocationManager
import android.provider.Settings
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.google.android.gms.maps.model.LatLng

@Preview(
    showBackground = true,
    name = "smallDevice",
    device = "spec:width=411dp,height=891dp",
)
@Preview(
    showBackground = true,
    name = "smallDevice - darkMode",
    device = "spec:width=411dp,height=891dp",
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    showBackground = true,
    name = "smallDevice Landscape",
    device = "spec:width=411dp,height=891dp,orientation=landscape",
)
@Preview(
    showBackground = true,
    name = "Pixel 5",
    device = "spec:parent=pixel_5",
)
@Preview(
    showBackground = true,
    name = "Pixel 5 Landscape",
    device = "spec:parent=pixel_5,orientation=landscape",
)
annotation class StandardPreview

fun Double?.to2DecimalPlaces(): String? = "%.2f".format(this ?: 0.0)

fun Context.isLocationEnabled(): Boolean {
    val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

fun Context.requestGpsPermission() {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    startActivity(intent)
}

fun Context.shouldRequestPermission(): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION,
    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    ) != PackageManager.PERMISSION_GRANTED
}

fun Context.openMapForDirection(latLng: LatLng) {
    val gmmIntentUri = "google.navigation:q=${latLng.latitude},${latLng.longitude}".toUri()
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    mapIntent.flags = FLAG_ACTIVITY_NEW_TASK
    if (mapIntent.resolveActivity(packageManager) != null) {
        startActivity(mapIntent)
    }
}

fun Context.openDialer() {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = "tel:1234567890".toUri()
    startActivity(intent)
}
