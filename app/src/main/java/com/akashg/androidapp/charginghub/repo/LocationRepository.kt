package com.akashg.androidapp.charginghub.repo

import android.annotation.SuppressLint
import android.content.Context
import com.akashg.androidapp.charginghub.R
import com.akashg.androidapp.charginghub.model.LocationInfo
import com.akashg.androidapp.charginghub.utils.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LocationRepository @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) {

    suspend fun getAllLocation(context: Context): List<LocationInfo> {
        return try {
            val json = Utils.readJsonFromRaw(context, R.raw.location)
            Gson().fromJson(json, Array<LocationInfo>::class.java).toList()
        } catch (e: Exception) {
            listOf()
        }
    }

    @SuppressLint("MissingPermission")
    @Throws(Exception::class)
    suspend fun getCurrentLocation(): LatLng = suspendCoroutine { cont ->
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    cont.resume(LatLng(location.latitude, location.longitude))
                } else {
                    cont.resumeWithException(Exception("Location is null"))
                }
            }
            .addOnFailureListener { e ->
                cont.resumeWithException(e)
            }
    }
}
