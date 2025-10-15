package com.example.tiktokandroid.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tiktokandroid.core.presentation.view.HomeScreens
import com.example.tiktokandroid.core.presentation.view.navigationItems

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = Color.White
    ) {
        navigationItems.forEachIndexed { index, item ->
            val isSelected = selectedNavigationIndex.intValue == index
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        // Conditionally select the icon based on the 'isSelected' state
                        imageVector = if (isSelected) item.selectedIcon else item.defaultIcon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        item.title,
                        // You can also use the 'isSelected' boolean here
                        color = if (isSelected) Color.Black else Color.Gray,
                        fontSize = 11.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray, // It's good practice to define this too
                    indicatorColor = Color.White
                )
            )
        }
    }
}