package com.stu25956.googlemaps.ui.screens

import android.location.Geocoder
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng

@Composable
fun InputScreen(navController: NavController) {
    // Mutable state for text fields
    var latitudeText by remember { mutableStateOf("") }
    var longitudeText by remember { mutableStateOf("") }
    var addressText by remember { mutableStateOf("") }

    // Enable button logic: The button is enabled if either lat/lng or address is entered
    val isButtonEnabled = remember(latitudeText, longitudeText, addressText) {
        (latitudeText.isNotBlank() && longitudeText.isNotBlank()) || addressText.isNotBlank()
    }

    // Get context
    val context = LocalContext.current

    // Function to get LatLng from address
    fun getLatLngFromAddress(address: String): LatLng? {
        val geocoder = Geocoder(context)

        return try {
            // Safely handle nullable list from geocoder API
            val results = geocoder.getFromLocationName(address, 1)

            // Return LatLng if results found
            if (!results.isNullOrEmpty()) {
                val location = results[0]
                LatLng(location.latitude, location.longitude)
            } else {
                // Return null if no results found
                null
            }
        } catch (e: Exception) {
            // Handle any exceptions
            Toast.makeText(context, "Error trying to find address", Toast.LENGTH_SHORT).show()
            null
        }
    }

    // Box to center the content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {
        // Column to stack the content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            // Title for Latitude/Longitude input
            Text(
                textAlign = TextAlign.Center,
                text = "Enter Latitude and Longitude",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Latitude Text Input
            TextField(
                value = latitudeText,
                onValueChange = { latitudeText = it },
                label = { Text("Latitude") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Longitude Text Input
            TextField(
                value = longitudeText,
                onValueChange = { longitudeText = it },
                label = { Text("Longitude") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Divider with OR
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    thickness = 2.dp
                )
                Text(
                    text = "OR",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    thickness = 2.dp
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Title for Address input
            Text(
                text = "Enter Address",
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Address Input
            TextField(
                value = addressText,
                onValueChange = { newText -> addressText = newText },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    // Hide keyboard on Done
                    onDone = {
                    }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Button to show the map
            Button(
                onClick = {
                    // Navigate to map screen with lat/lng or address
                    if (latitudeText.isNotBlank() && longitudeText.isNotBlank()) {
                        // Use latitude and longitude
                        val lat = latitudeText.toDouble()
                        val lng = longitudeText.toDouble()
                        navController.navigate("mapScreen/$lat/$lng")
                    } else if (addressText.isNotBlank()) {
                        // Use address
                        val latLng = getLatLngFromAddress(addressText)
                        if (latLng != null) {
                            navController.navigate("mapScreen/${latLng.latitude}/${latLng.longitude}")
                        } else {
                            // Show error if address not found
                            Toast.makeText(context, "Address not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                // Enable button only if lat/lng or address is entered
                enabled = isButtonEnabled,
                modifier = Modifier
                    .width(300.dp)
                    .height(80.dp)
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Show on Map",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}