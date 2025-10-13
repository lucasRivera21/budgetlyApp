package com.lucasdev.budgetlyapp.common.utils

enum class UploadState(val code: Int) {
    DELETED(-1),
    DO_NOT_UPLOAD(0),
    UPLOADED(1),
    EDITED(2);

    companion object {
        fun codeToUploadState(code: Int): UploadState {
            return entries.firstOrNull { it.code == code } ?: DO_NOT_UPLOAD
        }
    }
}
