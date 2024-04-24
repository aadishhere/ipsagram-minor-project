package com.aadish.ipsagram.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.People
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Event : BottomBarScreen(
        route = "EVENT",
        title = "Event",
        icon = Icons.Default.DateRange
    )

    object Post : BottomBarScreen(
        route = "POST",
        title = "Add",
        icon = Icons.Default.Add

    )

    object Profile : BottomBarScreen(
        route = "PROFILE",
        title = "Profile",
        icon = Icons.Default.AccountCircle
    )

}
