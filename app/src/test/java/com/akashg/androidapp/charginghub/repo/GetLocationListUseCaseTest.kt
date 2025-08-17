package com.akashg.androidapp.charginghub.repo

import android.content.Context
import com.akashg.androidapp.charginghub.model.LocationInfo
import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetLocationListUseCaseTest {

    private val locationRepository: LocationRepository = mockk(relaxed = true)
    private val getLocationListUseCase = GetLocationListUseCase(locationRepository)
    private val context: Context = mockk()
    private val latLng: LatLng = LatLng(0.0, 0.0)

    @Test
    fun `getLocationRepository returns the correct instance`() = runTest {
        getLocationListUseCase(context, latLng)
        coVerify { locationRepository.getAllLocation(context) }
    }

    @Test
    fun `getLocationRepository returns sort list`() = runTest {
        coEvery { locationRepository.getAllLocation(context) } returns listOf(
            LocationInfo(11.0, 8.0, "Location A"),
            LocationInfo(3.0, 4.0, "Location B"),
            LocationInfo(8.0, 4.0, "Location c"),
        )

        val result = getLocationListUseCase(context, latLng)
        with(result.first()) {
            assertThat(latitude).isEqualTo(3.0)
            assertThat(longitude).isEqualTo(4.0)
            assertThat(name).isEqualTo("Location B")
            assertThat(distance).isNotNull()
        }
        with(result.last()) {
            assertThat(latitude).isEqualTo(11.0)
            assertThat(longitude).isEqualTo(8.0)
            assertThat(name).isEqualTo("Location A")
            assertThat(distance).isNotNull()
        }
    }
}
