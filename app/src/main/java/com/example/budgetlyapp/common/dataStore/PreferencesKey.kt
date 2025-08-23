package com.example.budgetlyapp.common.dataStore

interface PreferencesKey {
    val key: String
}

object EmailKey : PreferencesKey {
    override val key: String
        get() = "email"
}

object UserNameKey : PreferencesKey {
    override val key: String
        get() = "user_name"
}

object UserLastNameKey : PreferencesKey {
    override val key: String
        get() = "user_last_name"
}

object IncomeValueKey : PreferencesKey {
    override val key: String
        get() = "income_value"
}

object IsFirstTimeKey : PreferencesKey {
    override val key: String
        get() = "is_first_time"
}

object LastExecuteTaskWorkerKey : PreferencesKey {
    override val key: String
        get() = "last_execute_task_worker"
}