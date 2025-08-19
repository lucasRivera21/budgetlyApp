package com.example.budgetlyapp.features.profile.data

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.example.budgetlyapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface EditPersonalDataTask {
    suspend fun updateIncomeValue(incomeValue: String): Result<String>
}

class EditPersonalDataRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : EditPersonalDataTask {
    override suspend fun updateIncomeValue(incomeValue: String): Result<String> {
        val userId = auth.currentUser?.uid ?: return Result.failure(
            Throwable(
                getString(
                    context,
                    R.string.error_user_not_found
                )
            )
        )

        return try {
            db.collection("users").document(userId).update("incomeValue", incomeValue).await()
            Result.success("")
        } catch (e: Exception) {
            Result.failure(Throwable(getString(context, R.string.error_error_unknown)))
        }
    }
}