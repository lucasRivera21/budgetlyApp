package com.lucasdev.budgetlyapp.features.profile.domain.models

import com.lucasdev.budgetlyapp.R

data class ElementProfileModel(
    val id: String,
    val icon: Int,
    val title: String,
    val iconRight: Int? = R.drawable.ic_row_right,
)
