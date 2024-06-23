package com.example.testapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.testapp.navigation.TopLevelDestination

@Composable
fun ZamovGradientBackground(
    //modifier: Modifier = Modifier,
    //gradientColors: GradientColors = LocalGradientColors.current,
    snackbarHostState: SnackbarHostState,
    topLevelDestination: TopLevelDestination,
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {

    /*val brush = Brush.verticalGradient(
        0f to Color(0xFF3048CC),
        0.5f to Color(0xFF1D2E88),
        1f to Color(0xFF070D2C),
    )*/
    val backGroundColor = getBackgroundColor(topLevelDestination)



    Scaffold(
//containerColor = Color.Transparent
//containerColor = Color.Black
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .background(brush = backGroundColor)
                .padding(paddingValues)
        ) {
            content(paddingValues)
        }
    }
}


fun getBackgroundColor(topLevelDestination: TopLevelDestination): Brush {
    return when (topLevelDestination) {

        TopLevelDestination.VEICULOS -> Brush.horizontalGradient(
            0f to Color(0xFF1D1B1B),
            1f to Color(0xFF1D1B1B)
        )

        else -> Brush.verticalGradient(
            /*0f to UserInfoGrey,
            1f to UserInfoGrey,*/
            0f to Color(0xFF1931B1),
            0.5f to Color(0xFF1D2E88),
            1f to Color(0xFF070D2C),
        )
    }
}

/*
@Composable
@Preview
fun previewZamovGradientBackground() {
    ZamovGradientBackground({}) {
        //Text(text = "HELLO")
    }
}*/