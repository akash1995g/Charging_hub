package com.akashg.androidapp.charginghub.ui

import android.content.Context
import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.turbineScope
import com.akashg.androidapp.charginghub.model.LocationInfo
import com.akashg.androidapp.charginghub.repo.GetCurrentLocationUseCase
import com.akashg.androidapp.charginghub.repo.GetLocationListUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class HomeMapViewModelTest {
    private val getLocationListUseCase: GetLocationListUseCase = mockk()
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase = mockk()
    private val context: Context = mockk()
    private lateinit var viewModel: HomeMapViewModel

    private val list = listOf(
        LocationInfo(
            1.0,
            8.0,
            "test",
        ),
        LocationInfo(
            0.0,
            0.0,
            "name",
        ),
    )
    private val latLng = LatLng(2.0, 0.0)

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        coEvery { getCurrentLocationUseCase() } returns latLng
        coEvery { getLocationListUseCase(context, latLng) } returns list
        viewModel = HomeMapViewModel(context, getLocationListUseCase, getCurrentLocationUseCase)
    }

    @Test
    fun `getUiState initial state`() {
        assertThat(viewModel.uiState.value).isEqualTo(HomeMapViewModel.UiState())
    }

    @Test
    fun `updateLocationList success`() = runTest {
        viewModel.updateLocationList(false)
        viewModel.uiState.testWithTurbine {
            skipItems(2)
            with(awaitItem()) {
                assertThat(locationList).isNotEmpty()
                assertThat(latLng).isEqualTo(latLng)
                assertThat(isLoading).isFalse()
                assertThat(selectedMarkPosition).isNull()
                assertThat(showMapView).isTrue()
                assertThat(isFocused).isFalse()
            }
        }
    }

    @Test
    fun `updateLocationList getLocationListUseCase throws exception`() = runTest {
        coEvery { getLocationListUseCase(context, latLng) } throws Exception()
        viewModel.updateLocationList(false)
        viewModel.uiState.testWithTurbine {
            assertThat(awaitItem().locationList).isEmpty()
        }
    }

    @Test
    fun `updateLocationList empty list from use case`() {
        coEvery { getLocationListUseCase(context, latLng) } returns list
        viewModel.updateLocationList(false)
        assertThat(viewModel.uiState.value.locationList).isEmpty()
    }

    @Test
    fun `updateLocationList multiple calls`() = runTest {
        coEvery { getLocationListUseCase(context, latLng) } returns list
        viewModel.updateLocationList(false)
        viewModel.uiState.testWithTurbine {
            skipItems(2)
            assertThat(awaitItem().locationList).isNotEmpty()
        }
        coEvery { getLocationListUseCase(context, latLng) } returns emptyList()
        viewModel.updateLocationList(false)
        viewModel.uiState.testWithTurbine {
            skipItems(2)
            assertThat(awaitItem().locationList).isEmpty()
        }
    }

    @Test
    fun `updateFocus to true`() {
        assertThat(viewModel.uiState.value.isFocused).isFalse()
        viewModel.updateFocus(true)
        assertThat(viewModel.uiState.value.isFocused).isTrue()
        viewModel.updateFocus(false)
        assertThat(viewModel.uiState.value.isFocused).isFalse()
    }

    @Test
    fun `toggleView from true to false`() {
        viewModel.toggleView()
        assertThat(viewModel.uiState.value.showMapView).isFalse()
        assertThat(viewModel.uiState.value.isFocused).isFalse()
        viewModel.toggleView()
        assertThat(viewModel.uiState.value.showMapView).isTrue()
        assertThat(viewModel.uiState.value.isFocused).isFalse()
    }

    @Test
    fun `updateSelectedMarkPosition with valid LatLng`() {
        viewModel.updateSelectedMarkPosition(LatLng(8.0, 78.0))
        assertThat(viewModel.uiState.value.selectedMarkPosition).isEqualTo(LatLng(8.0, 78.0))
        assertThat(viewModel.uiState.value.showMapView).isTrue()
        assertThat(viewModel.uiState.value.isFocused).isFalse()
    }

    @Test
    fun `updateSelectedMarkPositionByIndex valid index`() = runTest {
        viewModel.uiState.testWithTurbine {
            skipItems(1)
            viewModel.updateLocationList(false)
            skipItems(1)
            viewModel.updateSelectedMarkPositionByIndex(0)
            assertThat(viewModel.uiState.value.selectedMarkPosition).isEqualTo(LatLng(1.0, 8.0))
            assertThat(viewModel.uiState.value.showMapView).isTrue()
            assertThat(viewModel.uiState.value.isFocused).isFalse()
        }
    }

    @Test
    fun `closeSearchBar when isFocused is true`() {
        viewModel.closeSearchBar()
        assertThat(viewModel.uiState.value.isFocused).isFalse()
    }

    @Test
    fun `requestPermission when shouldRequestPermission is true`() = runTest {
        viewModel.updateLocationList(true)
        viewModel.uiState.testWithTurbine {
            skipItems(1)
            assertThat(awaitItem().requestPermission).isTrue()
        }
    }

    @Test
    fun `permissionGranted when shouldRequestPermission is false`() = runTest {
        viewModel.updateLocationList(false)
        viewModel.permissionGranted()
        viewModel.uiState.testWithTurbine {
            skipItems(1)
            assertThat(awaitItem().requestPermission).isFalse()
        }
    }

    suspend fun <T> Flow<T>.testWithTurbine(
        block: suspend ReceiveTurbine<T>.() -> Unit,
    ) = turbineScope {
        val turbine = this@testWithTurbine.testIn(this)
        try {
            block(turbine)
        } finally {
            turbine.cancelAndIgnoreRemainingEvents()
        }
    }
}
