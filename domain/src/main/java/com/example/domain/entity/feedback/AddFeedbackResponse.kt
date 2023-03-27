package com.example.domain.entity.feedback


data class AddFeedbackResponse(
    val error: String?,
    val msg: String,
    val status: Boolean
)