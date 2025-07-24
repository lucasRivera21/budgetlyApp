package com.example.budgetlyapp.common.dataStore

interface PreferencesKey {
    val key: String
}

object UserNameKey : PreferencesKey {
    override val key: String
        get() = "user_name"

}

object IncomeValueKey : PreferencesKey {
    override val key: String
        get() = "income_value"
}