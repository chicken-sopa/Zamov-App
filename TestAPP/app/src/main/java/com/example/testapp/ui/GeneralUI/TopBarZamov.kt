package com.example.testapp.ui.GeneralUI

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarZamov(
    pageName: String = "ZAMOV APP",
    onNavigationClick: () -> Unit,
    scope: CoroutineScope
) {
    CenterAlignedTopAppBar(
        title = { Text(text = pageName, fontWeight = FontWeight.Bold) },

        navigationIcon = {
            IconButton(onClick = {
                onNavigationClick()
            }) {
                Icon(
                    Icons.Filled.Menu,
                    "Menu",
                    tint = Color.White
                    //modifier = Modifier.aspectRatio(0.06f)

                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White
        )
    )
}