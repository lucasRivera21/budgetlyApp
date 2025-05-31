package com.example.budgetlyapp

interface Collections {
    val collectionName: String
}

object UsersCollection : Collections {
    override val collectionName: String
        get() = "users"
}