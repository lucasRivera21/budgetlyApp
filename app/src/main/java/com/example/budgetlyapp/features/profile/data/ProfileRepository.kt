package com.example.budgetlyapp.features.profile.data

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.example.budgetlyapp.R
import com.example.budgetlyapp.UsersCollection
import com.example.budgetlyapp.features.profile.domain.models.ProfileModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface ProfileTask {
    suspend fun fetchUserInfo(): Result<ProfileModel>
}

class ProfileRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ProfileTask {
    override suspend fun fetchUserInfo(): Result<ProfileModel> {
        val currentUser = auth.currentUser ?: return Result.failure(
            Exception(
                getString(
                    context,
                    R.string.error_user_not_found
                )
            )
        )

        val userId = currentUser.uid

        return try {
            val email = currentUser.email ?: ""

            val userRef = db.collection(UsersCollection.collectionName).document(userId)
            val snapshot = userRef.get().await()
            val name = snapshot.getString("name") ?: ""
            val lastName = snapshot.getString("lastName") ?: ""

            Result.success(ProfileModel(email = email, name = name, lastName = lastName))
        } catch (e: Exception) {
            Result.failure(Exception(getString(context, R.string.error_error_unknown)))
        }
    }
}