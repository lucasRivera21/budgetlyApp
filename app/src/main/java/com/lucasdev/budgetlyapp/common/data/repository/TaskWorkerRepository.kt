package com.lucasdev.budgetlyapp.common.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lucasdev.budgetlyapp.TaskCollection
import com.lucasdev.budgetlyapp.UsersCollection
import com.lucasdev.budgetlyapp.common.data.AppDatabase
import com.lucasdev.budgetlyapp.common.domain.models.TaskByUploadStateDTO
import com.lucasdev.budgetlyapp.common.domain.models.TaskToUpload
import com.lucasdev.budgetlyapp.common.utils.UploadState
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

private const val TAG = "TaskWorkerRepository"

interface TaskWorkerRepository {
    suspend fun getTaskList(): List<TaskByUploadStateDTO>

    suspend fun uploadTask(task: TaskToUpload): String?

    suspend fun updateTaskIsUploaded(taskId: Int, taskIdRemote: String)

    suspend fun getTaskStringList(expenseId: Int): List<String>
}

class TaskWorkerRepositoryImpl @Inject constructor(
    private val room: AppDatabase,
    private val auth: FirebaseAuth,
    private val api: FirebaseFirestore
) :
    TaskWorkerRepository {
    override suspend fun getTaskList() = room.taskDao().getTasksByUploadState(
        expenseUploadState = UploadState.UPLOADED.code,
        taskUploadState = UploadState.UPLOADED.code
    )

    override suspend fun uploadTask(task: TaskToUpload): String? {
        return try {
            val user = auth.currentUser

            user?.let {
                val docRef = api.collection(UsersCollection.collectionName).document(user.uid)
                    .collection(TaskCollection.collectionName).add(task).await()

                docRef.id
            }
        } catch (e: Exception) {
            Log.e(TAG, "uploadTask: ", e)
            null
        }
    }

    override suspend fun updateTaskIsUploaded(taskId: Int, taskIdRemote: String) =
        room.taskDao().updateTaskIsUploadedByTaskId(
            taskId = taskId,
            taskIdRemote = taskIdRemote,
            isUpload = UploadState.UPLOADED.code
        )

    override suspend fun getTaskStringList(expenseId: Int) =
        room.taskDao().getTaskIdRemoteListByExpenseId(expenseId)
}