package com.example.budgetlyapp.features.main.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.budgetlyapp.ui.theme.AppTheme

@Composable
fun MainScreen() {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(bottomBar = {
        NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
            ItemListBottomNav.bottomNavItems.forEach { navItems ->
                val icon =
                    if (currentRoute == navItems.route) navItems.iconFilled else navItems.icon
                Log.d("MainScreen", "MainScreen: $navItems")
                NavigationBarItem(
                    selected = currentRoute == navItems.route,
                    onClick = {
                        bottomNavController.navigate(navItems.route)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = stringResource(navItems.title),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    },
                    label = {
                        Text(text = stringResource(navItems.title))
                    }
                )
            }
        }
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainNavigation(bottomNavController)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}