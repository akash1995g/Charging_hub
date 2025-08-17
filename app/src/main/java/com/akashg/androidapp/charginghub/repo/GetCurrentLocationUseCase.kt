package com.akashg.androidapp.charginghub.repo

import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(val locationRepository: LocationRepository) {
    suspend operator fun invoke(): LatLng {
        return try {
            locationRepository.getCurrentLocation()
        } catch (e: Exception) {
            LatLng(0.0, 0.0)
        }
    }
}
