package com.example.testapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.ui.graphics.vector.ImageVector


enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    //val iconTextId: Int,
    val title: String,

) {
    HOMEPAGE(
        title = "Pagina Principal",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    CONTRAORDENACOES(
        title = "Contraordenaçoes",
        selectedIcon = Icons.Filled.Warning,
        unselectedIcon = Icons.Outlined.Warning
    ),

    VEICULOS(
        title = "Veiculos",
        selectedIcon =  Icons.Filled.Refresh,
        unselectedIcon =Icons.Filled.Refresh,
),
   USER_PAGE(
       title = "Utilizador",
       selectedIcon =  Icons.Filled.AccountCircle,
       unselectedIcon =Icons.Filled.AccountCircle,
   )
}

