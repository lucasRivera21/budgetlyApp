package com.example.budgetlyapp.features.register.data.repository

import com.example.budgetlyapp.UsersCollection
import com.example.budgetlyapp.features.register.domain.model.RegisterUserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RegisterTask {
    suspend fun registerUser(email: String, password: String): Result<String>
    suspend fun registerUserInfo(registerUserModel: RegisterUserModel, uuid: String): Result<Unit>
}

class RegisterRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : RegisterTask {
    override suspend fun registerUser(email: String, password: String): Result<String> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(auth.currentUser?.uid!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registerUserInfo(
        registerUserModel: RegisterUserModel,
        uuid: String
    ): Result<Unit> {
        return try {
            db.collection(UsersCollection.collectionName).document(uuid)
                .set(registerUserModel).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}