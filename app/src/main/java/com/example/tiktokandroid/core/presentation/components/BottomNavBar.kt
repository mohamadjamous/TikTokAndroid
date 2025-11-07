package com.example.tiktokandroid.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tiktokandroid.core.presentation.model.BottomBarDestination
import com.example.tiktokandroid.theme.Black
import com.example.tiktokandroid.theme.Gray
import com.example.tiktokandroid.theme.White
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(94.dp),
        containerColor = if (selectedIndex.intValue == 0 || selectedIndex.intValue == 2) Black else White,
        tonalElevation = 4.dp
    ) {


        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                BottomBarDestination.values().forEachIndexed { index, screen ->
                    val isSelected = selectedIndex.intValue == index

                    // Handle icon size for ADD button
                    val (iconSize, offsetY) = if (screen == BottomBarDestination.UPLOAD) Pair(
                        42.dp,
                        (-8).dp
                    )
                    else Pair(22.dp, 0.dp)

                    val iconRes = when {
                        screen == BottomBarDestination.UPLOAD -> screen.unFilledIcon
                        isSelected -> screen.filledIcon ?: screen.unFilledIcon
                        else -> screen.unFilledIcon
                    }


                    this@NavigationBar.NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            selectedIndex.intValue = index
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            iconRes?.let {
                                Icon(
                                    painter = painterResource(id = it),
                                    contentDescription = screen.title?.let { stringResource(id = it) },
                                    modifier = Modifier
                                        .size(iconSize)
                                        .offset(y = offsetY),
                                    tint = Color.Unspecified
                                )
                            }
                        },
                        label = {
                            screen.title?.let {
                                Text(
                                    text = stringResource(id = it),
                                    fontSize = 11.sp,
                                    color =
                                        // Dark
                                        if (selectedIndex.intValue == 0 || selectedIndex.intValue == 2) {
                                            if (isSelected) White else Gray
                                        }
                                        // Light
                                        else {
                                            if (isSelected) Black else Gray
                                        },

                                    )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.Gray
                        )
                    )
                }
            }
        }
    }
}
