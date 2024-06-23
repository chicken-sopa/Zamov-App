package com.example.testapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.testapp.navigation.ZamovNavHost
import com.example.testapp.ui.GeneralUI.SideMenuZamov
import com.example.testapp.ui.GeneralUI.TopBarZamov
import com.example.testapp.ui.theme.ZamovGradientBackground


@Composable
fun ZamovApp(
    appState: ZamovAppState,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    ZamovGradientBackground(
        topLevelDestination = appState.currentTopLevelDestination,
        snackbarHostState = snackbarHostState
    ) { paddingValues ->

        SideMenuZamov(
            appState.drawerState.value,
            changeDrawerState = appState::changeDrawerState,
            currentTopLevelDestination = appState.currentTopLevelDestination,
            navigateTopLevelDestination = appState::navigateToTopLevelDestination,

            ) { scope ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent
            ) {

                Column(
                    modifier =Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .consumeWindowInsets(paddingValues)
                        .background(color = Color.Transparent),
                ) {

                    if (appState.showTopBar.value)
                        TopBarZamov(
                            onNavigationClick = appState::changeDrawerState,
                            scope = scope,
                            pageName = appState.currentTopLevelDestination.title ?: "NAME ERROR"
                        )

                    ZamovNavHost(
                        appState = appState,
                        showTopBar = appState::showTopBar,
                        hidTopBar = appState::hidTopBar,
                        onShowSnackbar = { message, action ->
                            snackbarHostState.showSnackbar(
                                message = message,
                                actionLabel = action,
                                duration = SnackbarDuration.Short,
                            ) == SnackbarResult.ActionPerformed
                        }
                    )

                }
            }

        }
    }
}