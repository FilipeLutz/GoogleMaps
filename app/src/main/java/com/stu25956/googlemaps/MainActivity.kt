package com.stu25956.googlemaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.maps.MapsInitializer
import com.google.android.libraries.places.api.Places
import com.stu25956.googlemaps.navigation.AppNavigation
import com.stu25956.googlemaps.ui.theme.GoogleMapsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Google Maps SDK
        MapsInitializer.initialize(applicationContext)

        // Google Places API directly with the key
        Places.initialize(applicationContext, "AIzaSyCOle444KogrzoizjjUMGXWP_nFMjTojkY")
        
        setContent {
            GoogleMapsTheme {
                AppNavigation()
            }
        }
    }
}