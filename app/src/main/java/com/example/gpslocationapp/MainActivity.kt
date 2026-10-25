package com.example.gpslocationapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity(), LocationListener {

    private lateinit var locationManager: LocationManager

    private var latitude by mutableStateOf("Waiting for GPS...")
    private var longitude by mutableStateOf("Waiting for GPS...")
    private var status by mutableStateOf("")

    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {
                startLocationUpdates()
            } else {
                status = "Location permission denied"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationManager =
            getSystemService(LOCATION_SERVICE) as LocationManager

        setContent {

            MaterialTheme {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Current GPS Location",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "Latitude: $latitude",
                        modifier = Modifier.padding(top = 24.dp)
                    )

                    Text(
                        text = "Longitude: $longitude",
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    if (status.isNotEmpty()) {
                        Text(
                            text = status,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }

                    Button(
                        onClick = {
                            checkLocationPermission()
                        },
                        modifier = Modifier.padding(top = 24.dp)
                    ) {
                        Text("Get Location")
                    }
                }
            }
        }

        checkLocationPermission()
    }

    private fun checkLocationPermission() {

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        } else {
            permissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun startLocationUpdates() {

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            status = "Location permission required"
            return
        }

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            status = "GPS is disabled"
            return
        }

        // Remove previous listener before requesting again
        locationManager.removeUpdates(this)

        status = "Waiting for GPS..."

        // Try the most recent GPS location first
        val lastLocation =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        if (lastLocation != null) {
            latitude = lastLocation.latitude.toString()
            longitude = lastLocation.longitude.toString()
        }

        // Continue listening for new GPS coordinates
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            500L,
            0f,
            this
        )
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude.toString()
        longitude = location.longitude.toString()
        status = "GPS location received"
    }

    override fun onDestroy() {
        super.onDestroy()

        if (::locationManager.isInitialized) {
            locationManager.removeUpdates(this)
        }
    }
}