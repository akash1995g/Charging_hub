package com.akashg.androidapp.charginghub.ui

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akashg.androidapp.charginghub.R
import com.akashg.androidapp.charginghub.model.LocationInfo
import com.akashg.androidapp.charginghub.ui.components.LoadingScreen
import com.akashg.androidapp.charginghub.ui.components.MapLocationDetailsCard
import com.akashg.androidapp.charginghub.ui.components.MenuButton
import com.akashg.androidapp.charginghub.ui.components.MenuLocationDetailsCard
import com.akashg.androidapp.charginghub.ui.components.RequestLocationPermission
import com.akashg.androidapp.charginghub.ui.components.SearchBar
import com.akashg.androidapp.charginghub.ui.components.SearchItem
import com.akashg.androidapp.charginghub.ui.theme.customColors
import com.akashg.androidapp.charginghub.utils.isLocationEnabled
import com.akashg.androidapp.charginghub.utils.openDialer
import com.akashg.androidapp.charginghub.utils.openMapForDirection
import com.akashg.androidapp.charginghub.utils.requestGpsPermission
import com.akashg.androidapp.charginghub.utils.shouldRequestPermission
import com.akashg.androidapp.charginghub.utils.to2DecimalPlaces
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.DefaultMapUiSettings
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.math.roundToInt

const val TEST_TAG_MENU_SECTION = "MenuSection"
const val TEST_TAG_SEARCH_SECTION = "SearchSection"
const val TEST_TAG_MAP_CARD_INFO_SECTION = "MapCardInfoSection"

@Composable
fun HomeMapScreen(viewModel: HomeMapViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RequestLocationPermission(
        uiState.requestPermission,
        viewModel::permissionGranted,
    ) {
        HomeMapScreenContent(
            locationList = uiState.locationList,
            showMapView = uiState.showMapView,
            toggleView = viewModel::toggleView,
            updateListWithNewCurrentLatLng = viewModel::updateLocationList,
            isFocused = uiState.isFocused,
            onFocusChange = viewModel::updateFocus,
            latLng = uiState.latLng,
            selectedMarkPosition = uiState.selectedMarkPosition,
            updateSelectedMarkPosition = viewModel::updateSelectedMarkPosition,
            updateSelectedMarkPositionByIndex = viewModel::updateSelectedMarkPositionByIndex,
            closeSearchBar = viewModel::closeSearchBar,
        )
        if (uiState.isLoading) {
            LoadingScreen(message = "Fetching Latest Locations...")
        }
    }
}

@Composable
private fun HomeMapScreenContent(
    locationList: List<LocationInfo>,
    toggleView: () -> Unit,
    showMapView: Boolean,
    isFocused: Boolean,
    onFocusChange: (Boolean) -> Unit = {},
    updateListWithNewCurrentLatLng: (Boolean) -> Unit,
    latLng: LatLng,
    selectedMarkPosition: LatLng?,
    updateSelectedMarkPosition: (LatLng) -> Unit,
    updateSelectedMarkPositionByIndex: (Int) -> Unit,
    closeSearchBar: () -> Unit,
) {
    val context = LocalContext.current
    val markerState = remember(latLng) { MarkerState(position = latLng) }
    val name = remember { mutableStateOf("") }

    val cameraPositionState = remember(selectedMarkPosition ?: latLng) {
        CameraPositionState(
            position = CameraPosition.fromLatLngZoom(
                selectedMarkPosition ?: latLng,
                12f,
            ),
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        var horizontalCardIndex by remember(showMapView, isFocused) {
            mutableIntStateOf(-1)
        }
        GoogleMap(
            modifier = Modifier.testTag("Map"),
            uiSettings = DefaultMapUiSettings.copy(
                zoomControlsEnabled = false,
                compassEnabled = false,
                indoorLevelPickerEnabled = false,
                mapToolbarEnabled = false,
                myLocationButtonEnabled = false,
            ),
            cameraPositionState = cameraPositionState,
        ) {
            locationList.forEachIndexed { index, it ->
                Marker(
                    state = rememberMarkerState(
                        position = LatLng(it.latitude, it.longitude),
                    ),
                    title = it.name,
                    onClick = {
                        horizontalCardIndex = if (horizontalCardIndex != index) {
                            index
                        } else {
                            -1
                        }
                        true
                    },
                )
            }

            Marker(
                state = markerState,
                title = "Current Location",
                icon = BitmapDescriptorFactory.fromResource(R.drawable.icons8_car),
            )
        }
        MainScreen(
            modifier = Modifier.background(
                color = if (!showMapView || isFocused) MaterialTheme.customColors.whiteColor else Color.Unspecified,
            ),
            icon = if (showMapView) R.drawable.icon_charging_hubs else R.drawable.icon_map,
            isFocused = isFocused,
            toggleView = toggleView,
            value = name.value,
            onValueChange = {
                name.value = it
            },
            closeSearchBar = closeSearchBar,
            onFocusChange = onFocusChange,
        ) {
            when {
                isFocused -> {
                    SearchSection(locationList, name, updateSelectedMarkPosition)
                }

                !showMapView -> {
                    MenuSection(context, locationList, updateSelectedMarkPositionByIndex)
                }

                else -> {
                    MapCardInfoSection(
                        context,
                        updateListWithNewCurrentLatLng,
                        horizontalCardIndex,
                        locationList,
                        updateSelectedMarkPositionByIndex,
                    )
                }
            }
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    if (!isFocused) {
        keyboardController?.hide()
        focusManager.clearFocus()
        name.value = ""
    }
}

@Composable
private fun MapCardInfoSection(
    context: Context,
    updateListWithNewCurrentLatLng: (Boolean) -> Unit,
    horizontalCardIndex: Int,
    locationList: List<LocationInfo>,
    updateSelectedMarkPositionByIndex: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.testTag(TEST_TAG_MAP_CARD_INFO_SECTION),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            MenuButton(
                R.drawable.icon_gps_fixed,
                contentDescription = stringResource(R.string.get_current_location),
            ) {
                if (context.isLocationEnabled()) {
                    updateListWithNewCurrentLatLng(context.shouldRequestPermission())
                } else {
                    context.requestGpsPermission()
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        if (horizontalCardIndex != -1) {
            HorizontalCardList(context, locationList, horizontalCardIndex) {
                updateSelectedMarkPositionByIndex(it)
            }
        }
    }
}

@Composable
private fun SearchSection(
    locationList: List<LocationInfo>,
    name: MutableState<String>,
    updateSelectedMarkPosition: (LatLng) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .testTag(TEST_TAG_SEARCH_SECTION)
            .fillMaxSize()
            .background(color = MaterialTheme.customColors.whiteColor),
    ) {
        locationList.filter {
            if (name.value.isNotEmpty()) {
                it.name.contains(name.value, ignoreCase = true)
            } else {
                false
            }
        }.forEach {
            item {
                SearchItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            updateSelectedMarkPosition(
                                LatLng(
                                    it.latitude,
                                    it.longitude,
                                ),
                            )
                        },
                    locationName = it.name,
                    locationAddress = "Location Address Distance - ${it.distance.to2DecimalPlaces()}",
                )
            }
        }
    }
}

@Composable
private fun MenuSection(
    context: Context,
    locationList: List<LocationInfo>,
    updateSelectedMarkPositionByIndex: (Int) -> Unit,
) {
    Spacer(modifier = Modifier.height(16.dp))
    LazyColumn(
        modifier = Modifier
            .testTag(TEST_TAG_MENU_SECTION)
            .fillMaxSize(),
    ) {
        itemsIndexed(locationList) { index, it ->
            MenuLocationDetailsCard(
                modifier = Modifier.clickable {
                    updateSelectedMarkPositionByIndex(index)
                },
                place = it.name,
                distance = "${it.distance?.roundToInt()}KM",
                callAction = {
                    context.openDialer()
                },
                navigateAction = {
                    context.openMapForDirection(LatLng(it.latitude, it.longitude))
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun TopSection(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    value: String,
    onValueChange: (String) -> Unit,
    isFocused: Boolean = false,
    onFocusChange: (Boolean) -> Unit = {},
    closeSearchBar: () -> Unit,
    toggleView: () -> Unit,
) {
    Row(modifier = modifier) {
        SearchBar(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onValueChange,
            isFocused = isFocused,
            closeSearchBar = closeSearchBar,
            onFocusChange = onFocusChange,
        )
        Spacer(Modifier.width(16.dp))
        MenuButton(icon, contentDescription = stringResource(R.string.menu_option)) {
            toggleView()
        }
    }
}

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    toggleView: () -> Unit,
    isFocused: Boolean,
    onFocusChange: (Boolean) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    closeSearchBar: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column(modifier) {
        Spacer(Modifier.height(21.dp))
        TopSection(
            modifier = Modifier.padding(horizontal = 16.dp),
            icon = icon,
            isFocused = isFocused,
            onFocusChange = onFocusChange,
            value = value,
            onValueChange = onValueChange,
            closeSearchBar = closeSearchBar,
            toggleView = toggleView,
        )
        content()
    }
}

@Composable
private fun HorizontalCardList(
    context: Context,
    locationList: List<LocationInfo>,
    horizontalCardIndex: Int,
    onScroll: (Int) -> Unit,
) {
    val listState = remember(horizontalCardIndex) {
        LazyListState(horizontalCardIndex)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.map { index -> index }
            .distinctUntilChanged().collect { index ->
                onScroll(index)
            }
    }

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(22.dp),
    ) {
        itemsIndexed(locationList) { index, item ->
            MapLocationDetailsCard(
                modifier = Modifier.fillParentMaxWidth(),
                title = item.name,
                distance = item.distance?.to2DecimalPlaces() ?: "Nil",
                callAction = {
                    context.openDialer()
                },
                navigateAction = {
                    context.openMapForDirection(LatLng(item.latitude, item.longitude))
                },
            )
        }
    }
}

@Preview
@Composable
private fun HomeMapScreenPreview() {
    HomeMapScreenContent(
        locationList = listOf(),
        toggleView = {},
        showMapView = false,
        isFocused = false,
        updateListWithNewCurrentLatLng = {},
        latLng = LatLng(0.0, 0.0),
        selectedMarkPosition = null,
        updateSelectedMarkPosition = {},
        closeSearchBar = {},
        onFocusChange = {},
        updateSelectedMarkPositionByIndex = {},
    )
}
