package com.akashg.androidapp.charginghub.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akashg.androidapp.charginghub.model.LocationInfo
import com.akashg.androidapp.charginghub.repo.GetCurrentLocationUseCase
import com.akashg.androidapp.charginghub.repo.GetLocationListUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMapViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val getLocationListUseCase: GetLocationListUseCase,
    val getCurrentLocationUseCase: GetCurrentLocationUseCase,
) : ViewModel() {

    data class UiState(
        val requestPermission: Boolean = false,
        val locationList: List<LocationInfo> = emptyList(),
        val showMapView: Boolean = true,
        val isFocused: Boolean = false,
        val latLng: LatLng = LatLng(0.0, 0.0),
        val selectedMarkPosition: LatLng? = null,
        val isLoading: Boolean = false,
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateLocationList(shouldRequestPermission: Boolean) = viewModelScope.launch {
        if (shouldRequestPermission) {
            requestPermission()
        } else {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val latLng = getCurrentLocationUseCase()
            val locationList = try {
                getLocationListUseCase(context, latLng)
            } catch (e: Exception) {
                listOf()
            }
            _uiState.update {
                it.copy(
                    locationList = locationList,
                    latLng = latLng,
                    isLoading = false,
                    selectedMarkPosition = null,
                )
            }
        }
    }

    fun updateFocus(boolean: Boolean) {
        _uiState.update {
            it.copy(isFocused = boolean)
        }
    }

    fun toggleView() {
        _uiState.update {
            it.copy(showMapView = !it.showMapView, isFocused = false)
        }
    }

    fun updateSelectedMarkPosition(latLng: LatLng) {
        _uiState.update {
            it.copy(selectedMarkPosition = latLng, showMapView = true, isFocused = false)
        }
    }

    fun updateSelectedMarkPositionByIndex(index: Int) {
        _uiState.value.locationList.get(index).also {
            updateSelectedMarkPosition(LatLng(it.latitude, it.longitude))
        }
    }

    fun closeSearchBar() {
        _uiState.update {
            it.copy(isFocused = false)
        }
    }

    private fun requestPermission() {
        _uiState.update {
            it.copy(requestPermission = true)
        }
    }

    fun permissionGranted() {
        _uiState.update {
            it.copy(requestPermission = false)
        }
    }
}
