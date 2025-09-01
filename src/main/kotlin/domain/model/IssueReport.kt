package com.simbiri.domain.model

data class IssueReport(
    val id: String? = null,
    val questionId: String,
    val issueType: String,
    val additionalComment: String?,
    val userEmail: String?,
    val timeStamp: String
)
