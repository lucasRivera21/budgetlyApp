package com.example.budgetlyapp.features.profile.domain.models

interface ElementProfileId {
    val id: String
}

object PersonalData : ElementProfileId {
    override val id: String
        get() = "personal_data"
}

object ChangePassword : ElementProfileId {
    override val id: String
        get() = "change_password"
}

object Logout : ElementProfileId {
    override val id: String
        get() = "logout"
}