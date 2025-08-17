package com.akashg.androidapp.charginghub.repo

import android.content.Context
import com.akashg.androidapp.charginghub.model.LocationInfo
import com.akashg.androidapp.charginghub.utils.Utils
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class GetLocationListUseCase @Inject constructor(val locationRepository: LocationRepository) {

    suspend operator fun invoke(
        context: Context,
        latLng: LatLng,
    ): List<LocationInfo> {
        return locationRepository.getAllLocation(context).map {
            it.copy(
                distance = Utils.distanceBetweenTowPoints(
                    it.latitude,
                    it.longitude,
                    latLng.latitude,
                    latLng.longitude,
                ),
            )
        }.sortedBy { it.distance }
    }
}
