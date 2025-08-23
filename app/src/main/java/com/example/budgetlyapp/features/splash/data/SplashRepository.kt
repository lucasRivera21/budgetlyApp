package com.example.budgetlyapp.features.splash.data

import com.example.budgetlyapp.UsersCollection
import com.example.budgetlyapp.features.splash.domain.models.UserInfoModule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface SplashTask {
    suspend fun fetchUserInfo(): UserInfoModule
}

class SplashRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) :
    SplashTask {
    override suspend fun fetchUserInfo(): UserInfoModule {
        val firebaseUser = auth.currentUser ?: return UserInfoModule()
        val userId = firebaseUser.uid
        val email = firebaseUser.email ?: ""

        val userRef = db.collection(UsersCollection.collectionName).document(userId)

        val userSnapshot = userRef.get().await()
        val userData = userSnapshot.data
        val userName = userData?.get("name") as? String ?: ""
        val userLastName = userData?.get("lastName") as? String ?: ""
        val incomeValue = userData?.get("incomeValue") as? String ?: "0.0"

        return UserInfoModule(
            email = email,
            userName = userName,
            userLastName = userLastName,
            incomeValue = incomeValue.toDouble()
        )
    }
}