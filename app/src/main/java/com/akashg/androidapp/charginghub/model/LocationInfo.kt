package com.akashg.androidapp.charginghub.model

data class LocationInfo(
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val distance: Double? = null,
)
