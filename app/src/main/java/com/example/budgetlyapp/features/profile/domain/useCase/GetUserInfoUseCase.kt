package com.example.budgetlyapp.features.profile.domain.useCase

import com.example.budgetlyapp.features.profile.data.ProfileTask
import com.example.budgetlyapp.features.profile.domain.models.ProfileModel
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val profileTask: ProfileTask) {
    suspend operator fun invoke(): Result<ProfileModel> {
        return profileTask.fetchUserInfo()
    }
}