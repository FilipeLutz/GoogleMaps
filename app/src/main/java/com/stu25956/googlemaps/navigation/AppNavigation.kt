package com.stu25956.googlemaps.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stu25956.googlemaps.ui.screens.InputScreen
import com.stu25956.googlemaps.ui.screens.MapScreen

@Composable
fun AppNavigation() {
    // NavController that manages the navigation routes
    val navController = rememberNavController()

    // NavHost composable that defines the navigation routes
    NavHost(navController, startDestination = "inputScreen") {
        // Composable that represents the input screen
        composable("inputScreen") { InputScreen(navController) }
        // Composable that represents the map screen
        composable("mapScreen/{lat}/{lng}") { backStackEntry ->
            // Retrieve the latitude and longitude from the arguments
            val lat = backStackEntry.arguments?.getString("lat")?.toDoubleOrNull() ?: 0.0
            // Retrieve the longitude from the arguments
            val lng = backStackEntry.arguments?.getString("lng")?.toDoubleOrNull() ?: 0.0
            // Pass the latitude and longitude to the MapScreen
            MapScreen(navController, lat, lng)
        }
    }
}