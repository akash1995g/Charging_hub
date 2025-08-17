package com.akashg.androidapp.charginghub.repo

import android.content.Context
import android.location.Location
import com.akashg.androidapp.charginghub.model.LocationInfo
import com.akashg.androidapp.charginghub.utils.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

class LocationRepositoryTest {

    private val fusedLocationProviderClient: FusedLocationProviderClient = mockk()
    private val locationRepository = LocationRepository(fusedLocationProviderClient)
    private val context: Context = mockk()

    @Test
    fun `getAllLocation valid JSON success`() = runTest {
        val fakeJson = """
            [
                {"name": "Location A", "latitude": 12.34, "longitude": 56.78},
                {"name": "Location B", "latitude": 90.12, "longitude": 34.56}
            ]
        """.trimIndent()

        val result = initialize(fakeJson)

        assertThat(result).hasSize(2)
        assertThat(result[0].name).isEqualTo("Location A")
        assertThat(result[1].latitude).isEqualTo(90.12)
        assertThat(result[1].longitude).isEqualTo(34.56)
    }

    @Test
    fun `getAllLocation empty JSON array`() = runTest {
        val fakeJson = """
            [
            ]
        """.trimIndent()
        val result = initialize(fakeJson)

        assertThat(result).isEmpty()
    }

    @Test
    fun `getAllLocation JSON with missing fields`() = runTest {
        val fakeJson = """
            [
                {"name": "Location A", "latitude": 12.34, "lon": 56.78},
                {"name": "Location B", "latitude": 90.12, "long": 34.56}
            ]
        """.trimIndent()

        val result = initialize(fakeJson)

        assertThat(result).hasSize(2)
        assertThat(result[0].name).isEqualTo("Location A")
        assertThat(result[1].latitude).isEqualTo(90.12)
        assertThat(result[1].longitude).isEqualTo(0.0)
    }

    @Test
    fun `getAllLocation JSON with extra fields`() = runTest {
        val fakeJson = """
            [
                {"name": "Location A", "latitude": 12.34, "longitude": 56.78, "extra": "value"}
            ]
        """.trimIndent()
        val result = initialize(fakeJson)

        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("Location A")
    }

    private suspend fun initialize(fakeJson: String): List<LocationInfo> {
        mockkObject(Utils)
        every { Utils.readJsonFromRaw(context, any()) } returns fakeJson
        return locationRepository.getAllLocation(context)
    }

    @Test
    fun `getAllLocation malformed JSON`() = runTest {
        val fakeJson = """
            [
               "value"
            ]
        """.trimIndent()
        val result = initialize(fakeJson)

        assertThat(result).isEmpty()
    }

    @Test
    fun `getAllLocation file not found`() = runTest() {
        every { Utils.readJsonFromRaw(context, any()) } throws IOException("error")
        val result = locationRepository.getAllLocation(context)
        assertThat(result).isEmpty()
    }

    @Test
    fun `getCurrentLocation success with latLng`() = runTest {
        val mockLocation = mockk<Location> {
            every { latitude } returns 12.34
            every { longitude } returns 56.78
        }

        val task = mockSuccessTask(mockLocation)
        mockFusedClient(task)

        val result = locationRepository.getCurrentLocation()
        assertThat(result).isNotNull()
        assertThat(result.latitude).isEqualTo(12.34)
        assertThat(result.longitude).isEqualTo(56.78)
    }

    @Test
    fun `getCurrentLocation failure with null latLng exception`() = runTest {
        val task = mockSuccessTask(null)
        mockFusedClient(task)

        try {
            locationRepository.getCurrentLocation()
        } catch (e: Exception) {
            assertThat(e.message).isEqualTo("Location is null")
        }
    }

    @Test
    fun `getCurrentLocation failure with exception`() = runTest {
        val task = mockFailureTask(Exception("error"))
        mockFusedClient(task)

        try {
            locationRepository.getCurrentLocation()
        } catch (e: Exception) {
            assertThat(e.message).isEqualTo("error")
        }
    }

    private fun mockSuccessTask(location: Location?): Task<Location> {
        val task = mockk<Task<Location>>()
        every { task.addOnSuccessListener(any()) } answers {
            val listener = firstArg<com.google.android.gms.tasks.OnSuccessListener<Location>>()
            listener.onSuccess(location)
            task
        }
        every { task.addOnFailureListener(any()) } answers { task }
        return task
    }

    private fun mockFailureTask(exception: Exception): Task<Location> {
        val task = mockk<Task<Location>>()
        every { task.addOnSuccessListener(any()) } answers { task }
        every { task.addOnFailureListener(any()) } answers {
            val listener = firstArg<com.google.android.gms.tasks.OnFailureListener>()
            listener.onFailure(exception)
            task
        }
        return task
    }

    private fun mockFusedClient(task: Task<Location>) {
        coEvery {
            fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null,
            )
        } returns task
    }
}
