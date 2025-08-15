package com.example.budgetlyapp.features.profile.domain.models

import com.example.budgetlyapp.R

data class ElementProfileModel(
    val id: String,
    val icon: Int,
    val title: String,
    val iconRight: Int? = R.drawable.ic_row_right,
)
