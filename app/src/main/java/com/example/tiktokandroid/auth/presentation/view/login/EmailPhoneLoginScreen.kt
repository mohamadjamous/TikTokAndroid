package com.example.tiktokandroid.auth.presentation.view.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiktokandroid.auth.data.model.TabItem
import com.example.tiktokandroid.core.presentation.components.BackButton

@Composable
fun EmailPhoneLoginScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {}
) {

    val tabs = listOf(
        TabItem("Phone"),
        TabItem("Email / Username")
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            BackButton() {
                onBackPressed()
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Log in",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1.3f))
        }



        ScrollableTabRow(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            selectedTabIndex = selectedTabIndex,
            edgePadding = 0.dp, // 👈 removes extra spacing
            containerColor = Color.Transparent,
            contentColor = Color.Gray,
            divider = {}, // optional: removes bottom divider
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = Color.Black,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(2.dp)
                )
            }
        )
        {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .height(48.dp) // 👈 makes tabs bigger
                        .widthIn(min = 170.dp)

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tab.title,
                            color = if (selectedTabIndex == index) Color.Black else Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }


            }
        }

        when (selectedTabIndex) {
            0 -> {
                // Tab 1 content
                PhoneLoginView()
            }

            1 -> {
                // Tab 2 content
                EmailLoginView()
            }
        }
    }


}

@Preview(showSystemUi = true)
@Composable
private fun EmailPhoneLoginScreenPreview() {

    EmailPhoneLoginScreen()
}