package com.example.testapp.ui.GeneralUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testapp.R
import com.example.testapp.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope


data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

@Composable
fun SideMenuZamov(
    drawerState: DrawerState,
    currentTopLevelDestination: TopLevelDestination,
    changeDrawerState: () -> Unit,
    navigateTopLevelDestination: (TopLevelDestination) -> Unit,
    appBody: @Composable (scope: CoroutineScope) -> Unit,
) {

    val items = getSideMenuItems()
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "ZAMOV".uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(1f),
                )
                Spacer(modifier = Modifier.height(16.dp))
                TopLevelDestination.entries.forEachIndexed { index, topLevelDestination ->
                    if (topLevelDestination != TopLevelDestination.USER_PAGE) {
                        NavigationDrawerItem(
                            label = {
                                Text(text = topLevelDestination.title)
                            },
                            selected = topLevelDestination == currentTopLevelDestination,
                            //onClick = changeDrawerState,
                            onClick = {
                                navigateTopLevelDestination(topLevelDestination)
                                changeDrawerState()
                            },
                            icon = {
                                Icon(
                                    imageVector = if (topLevelDestination == currentTopLevelDestination) {
                                        topLevelDestination.selectedIcon
                                    } else topLevelDestination.unselectedIcon,
                                    contentDescription = topLevelDestination.title
                                )
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column {
                        Button(
                            onClick = {
                                //navigate.to(settings)
                                changeDrawerState()
                                navigateTopLevelDestination(TopLevelDestination.USER_PAGE)
                            },
                            //modifier = //Modifier.ali(Alignment.CenterHorizontally)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,

                                ) {
                                Text(text = "USERNAME")
                                Spacer(Modifier.width(16.dp))
                                Icon(
                                    imageVector = Icons.Filled.Settings,
                                    contentDescription = "Settings"
                                )
                            }


                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        },
        drawerState = drawerState,
        modifier = Modifier.background(Color.Transparent)
    ) {

        appBody(scope)
    }
}


@Composable
fun getSideMenuItems(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            title = "Pagina Principal",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItem(
            title = "Contraordenaçoes",
            selectedIcon = Icons.Filled.Warning,
            unselectedIcon = Icons.Outlined.Warning
        ),
        NavigationItem(
            title = "Carros",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.baseline_directions_car_24),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.baseline_directions_car_24),
        )
    )
}
