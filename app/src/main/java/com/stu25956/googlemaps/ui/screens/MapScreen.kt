package com.stu25956.googlemaps.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_MAGENTA
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ROSE
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(navController: NavController, latitude: Double, longitude: Double) {
    // Initial marker position
    val initialLatLng = LatLng(latitude, longitude)

    // State to hold the current color for the marker
    var markerColor by remember { mutableFloatStateOf(HUE_BLUE) }

    // Initialize CameraPositionState for camera view
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLatLng, 15f)
    }

    // Main Column with Google Map and buttons
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        // Main Column with Google Map and buttons
        Card(
            modifier = Modifier
                .height(450.dp)
                .padding(16.dp)
                .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.large)
        ) {
            // GoogleMap composable to display the map
            GoogleMap(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                // Display the marker with the current color
                Marker(
                    state = MarkerState(position = initialLatLng),
                    title = "Location",
                    snippet = "Lat: ${initialLatLng.latitude}, Lng: ${initialLatLng.longitude}",
                    icon = BitmapDescriptorFactory.defaultMarker(markerColor)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Button to change marker color
        Button(
            onClick = {
                // Change the color of the marker
                markerColor = nextMarkerColor(markerColor)
            },
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
                .padding(8.dp)
        ) {
            Text(
                "Change Marker Colour",
                style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Back Button to go back to the search screen
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .width(300.dp)
                .height(60.dp)
                .padding(8.dp)
        ) {
            Text(
                "Back",
                style = MaterialTheme.typography.titleMedium)
        }
    }
}

// Function to get the next color for the marker
fun nextMarkerColor(currentColor: Float): Float {
    return when (currentColor) {
        HUE_BLUE -> HUE_RED
        HUE_RED -> HUE_GREEN
        HUE_GREEN -> HUE_YELLOW
        HUE_YELLOW -> HUE_ORANGE
        HUE_ORANGE -> HUE_CYAN
        HUE_CYAN -> HUE_MAGENTA
        HUE_MAGENTA -> HUE_AZURE
        HUE_AZURE -> HUE_VIOLET
        HUE_VIOLET -> HUE_ROSE
        HUE_ROSE -> HUE_BLUE
        else -> HUE_BLUE
    }
}