package com.akashg.androidapp.charginghub.utils

import android.content.Context
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

object Utils {

    fun readJsonFromRaw(context: Context, resId: Int): String {
        return context.resources.openRawResource(resId)
            .bufferedReader()
            .use { it.readText() }
    }

    fun distanceBetweenTowPoints(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val radius = 6371.0

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2).pow(2) +
            cos(Math.toRadians(lat1)) *
            cos(Math.toRadians(lat2)) *
            sin(dLon / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return radius * c
    }
}
