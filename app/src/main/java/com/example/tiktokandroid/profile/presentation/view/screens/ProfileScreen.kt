package com.example.tiktokandroid.profile.presentation.view.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tiktokandroid.auth.presentation.components.LoginSignupSwitcher
import com.example.tiktokandroid.auth.presentation.view.LoginScreen
import com.example.tiktokandroid.auth.presentation.view.SignupScreen
import com.example.tiktokandroid.core.presentation.components.CustomButton
import com.example.tiktokandroid.core.presentation.components.LoadingEffect
import com.example.tiktokandroid.core.presentation.model.Post
import com.example.tiktokandroid.feed.data.model.FeedUiState

import com.example.tiktokandroid.profile.data.model.ProfilePagerTabs
import com.example.tiktokandroid.profile.presentation.components.PostRowItem
import com.example.tiktokandroid.profile.presentation.components.tabs.LikeVideoTab
import com.example.tiktokandroid.profile.presentation.components.tabs.PublicVideoTab
import com.example.tiktokandroid.profile.presentation.viewmodel.ProfileViewModel
import com.example.tiktokandroid.theme.PrimaryColor
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToEmailSignup: () -> Unit = {},
    navigateToPhoneSignup: (String) -> Unit = {},
    navigateToSettings: () -> Unit = {},
    navigateToEmailPhoneLogin: () -> Unit = {},
    onClickVideo: (video: Post, index: Int) -> Unit
) {

    val posts = viewModel.posts.collectAsState().value

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val partialHeight = screenHeight * 0.75f
    var loggedIn by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    val uiState = viewModel.uiState.value
    val currentUser by viewModel.currentUser

    val tabs = ProfilePagerTabs.values().asList()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    val scrollState = rememberScrollState()

    val coroutineScope = rememberCoroutineScope()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    LaunchedEffect(currentUser) {
        loggedIn = currentUser != null
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is FeedUiState.Error -> {
                error = true
                loading = false
            }

            FeedUiState.Idle -> {}
            FeedUiState.Loading -> {
                error = false
                loading = true
            }

            is FeedUiState.Success -> {
                error = false
                loading = false
            }
        }
    }


    Box(modifier = modifier.fillMaxSize()) {

        Column {

            // top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                Spacer(Modifier.weight(1.3f))

                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.weight(1f))

                IconButton(onClick = {
                    navigateToSettings()
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "More options")
                }

            }

            Divider(
                modifier = Modifier.padding(top = 5.dp)
            )

            // User logged in
            if (loggedIn) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = modifier
                        .fillMaxSize()
                        .padding(bottom = 55.dp),
                    contentPadding = PaddingValues(bottom = 50.dp)
                ) {
                    // Profile header spans all columns
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ProfileHeaderView(
                                modifier = Modifier.padding(top = 30.dp),
                                user = currentUser
                            )
                        }
                    }

                    // Loading state
                    if (loading) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                LoadingEffect()
                            }
                        }
                    }

                    // Error state
                    if (error) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Error fetching posts", color = Color.Red)
                            }
                        }
                    }


                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            TabRow(
                                selectedTabIndex = pagerState.currentPage,
                                indicator = {
                                    val modifier = Modifier
                                        .tabIndicatorOffset(it[pagerState.currentPage])
                                        .padding(horizontal = screenWidth / 5.5f)

                                    TabRowDefaults.Indicator(
                                        modifier = modifier,
                                        color = Black
                                    )
                                },
                                divider = { Divider(thickness = 0.5.dp, color = Color.Gray) },
                            ) {
                                tabs.forEachIndexed { index, item ->
                                    val isSelected = pagerState.currentPage == index
                                    Tab(
                                        selected = isSelected,
                                        onClick = {
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(index)
                                            }
                                        },
                                        selectedContentColor = Color.Transparent,
                                        icon = {
                                            Icon(
                                                painter = painterResource(id = item.icon),
                                                contentDescription = null,
                                                modifier = Modifier.size(18.dp),
                                                tint = if (isSelected) Black else Color.Gray
                                            )
                                        }
                                    )
                                }
                            }

                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) { page ->
                                when (page) {
                                    0 -> PublicVideoTab(
                                        viewModel = viewModel,
                                        scrollState = scrollState,
                                        onClickVideo = onClickVideo
                                    )

                                    1 -> LikeVideoTab(viewModel = viewModel)
                                }
                            }
                        }
                    }
                }

            } else {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(partialHeight),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        modifier = Modifier.size(150.dp),
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "More options"
                    )

                    Text(
                        text = "Login into existing account",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    CustomButton(
                        modifier = Modifier
                            .width(200.dp)
                            .height(65.dp)
                            .padding(top = 20.dp),
                        text = "Login",
                        containerColor = PrimaryColor,
                        contentColor = Color.White,
                        onClick = {
                            showBottomSheet = !showBottomSheet
                            scope.launch { sheetState.expand() }
                        }
                    )
                }
            }

        }


        if (showBottomSheet) {

            // Bottom sheet
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                containerColor = Color.White,
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxSize()
                    .navigationBarsPadding()
            )
            {
                // Sheet content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    // Top bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    )
                    {

                        IconButton(
                            onClick = {
                                scope
                                    .launch { sheetState.hide() }
                                    .invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "close"
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))


                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        LoginSignupSwitcher(
                            navigateToEmailSignup = navigateToEmailSignup,
                            navigateToPhoneSignup = navigateToPhoneSignup,
                            navigateToEmailPhoneLogin = navigateToEmailPhoneLogin
                        )
                    }


                }
            }

        }

    }

}


@Composable
@Preview(showSystemUi = true)
fun ProfileViewPreview() {
//    ProfileScreen()
}