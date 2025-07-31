package com.example.budgetlyapp

interface Collections {
    val collectionName: String
}

object UsersCollection : Collections {
    override val collectionName: String
        get() = "users"
}

object ExpenseCollection : Collections {
    override val collectionName: String
        get() = "expense"
}

object TaskCollection : Collections {
    override val collectionName: String
        get() = "task"
}