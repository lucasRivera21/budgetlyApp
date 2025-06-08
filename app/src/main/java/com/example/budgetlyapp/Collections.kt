package com.example.budgetlyapp

interface Collections {
    val collectionName: String
}

object UsersCollection : Collections {
    override val collectionName: String
        get() = "users"
}

object ExpenseGroupCollection : Collections {
    override val collectionName: String
        get() = "expenseGroup"
}

object ExpenseCollection : Collections {
    override val collectionName: String
        get() = "expense"

}