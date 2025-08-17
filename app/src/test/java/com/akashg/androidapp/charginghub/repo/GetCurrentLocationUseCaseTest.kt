package com.akashg.androidapp.charginghub.repo

import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetCurrentLocationUseCaseTest {
    private val locationRepository: LocationRepository = mockk()
    private val getCurrentLocationUseCase = GetCurrentLocationUseCase(locationRepository)

    @Test
    fun `locationRepository returns latLng`() = runTest {
        val latLng = LatLng(0.0, 0.0)
        coEvery { locationRepository.getCurrentLocation() } returns latLng
        val result = getCurrentLocationUseCase()
        assertThat(latLng).isEqualTo(result)
    }

    @Test
    fun `locationRepository throws exception`() = runTest {
        coEvery { locationRepository.getCurrentLocation() } throws Exception()
        val result = getCurrentLocationUseCase()
        assertThat(result).isEqualTo(LatLng(0.0, 0.0))
    }
}
