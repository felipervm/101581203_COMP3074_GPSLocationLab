# GPS Location App

COMP3074 Android Development Lab

Student: Felipe da Rocha Vieira Mattos  
Student ID: 101581203

## Description

This Android application displays the user's current latitude and longitude using the device GPS.

The app requests location permission at runtime and uses Android's built-in `LocationManager` with `LocationManager.GPS_PROVIDER` to obtain GPS location updates.

## Features

- Displays the current latitude
- Displays the current longitude
- Uses GPS specifically through `LocationManager.GPS_PROVIDER`
- Requests location permission at runtime
- Handles denied location permission
- Handles GPS disabled/unavailable state
- Includes a button to request or refresh the location

## Permissions

The application uses:

- `android.permission.ACCESS_FINE_LOCATION`
- `android.permission.ACCESS_COARSE_LOCATION`

These permissions are configured in `AndroidManifest.xml`, and fine location permission is also requested programmatically at runtime.

## Technologies

- Kotlin
- Android Studio
- Jetpack Compose
- Android LocationManager
- GPS Provider

## How It Works

The app creates a `LocationManager` and requests location updates using:

```kotlin
LocationManager.GPS_PROVIDER
```

When a GPS location is received, the application updates the interface with the current latitude and longitude.

## Run Instructions

1. Open the project in Android Studio.
2. Start an Android emulator or connect a physical Android device.
3. Run the application.
4. Allow precise location access when prompted.
5. If using an emulator, set a simulated GPS location from the emulator Location controls.
6. Press **Get Location** to request or refresh the GPS coordinates.

## Build

The project was successfully built with:

```powershell
.\gradlew.bat assembleDebug
```

## Repository

https://github.com/felipervm/101581203_COMP3074_GPSLocationLab
