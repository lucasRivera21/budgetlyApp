package com.example.budgetlyapp.features.main.presentation

import com.example.budgetlyapp.R
import com.example.budgetlyapp.features.main.domain.models.ItemsBottomNav
import com.example.budgetlyapp.navigation.ExpenseScreen
import com.example.budgetlyapp.navigation.HomeScreen
import com.example.budgetlyapp.navigation.ProfileScreen

object ItemListBottomNav {
    val bottomNavItems = listOf(
        ItemsBottomNav(
            title = R.string.main_expense_title,
            icon = R.drawable.ic_receipt,
            iconFilled = R.drawable.ic_receipt_fill,
            route = ExpenseScreen.route
        ),
        ItemsBottomNav(
            title = R.string.main_home_title,
            icon = R.drawable.ic_home,
            iconFilled = R.drawable.ic_home_fill,
            route = HomeScreen.route
        ),
        ItemsBottomNav(
            title = R.string.main_profile_title,
            icon = R.drawable.ic_profile,
            iconFilled = R.drawable.ic_profile_fill,
            route = ProfileScreen.route
        )
    )
}