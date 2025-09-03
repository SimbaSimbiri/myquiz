package com.simbiri.presentation.validator

import com.simbiri.domain.model.IssueReport
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateIssueReport() {
    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    validate<IssueReport> {issueReport ->
        when{
            issueReport.issueType.isBlank() -> {
                ValidationResult.Invalid(reason = "Issue type must be provided")
            }
            issueReport.questionId.isBlank() -> {
                ValidationResult.Invalid(reason = "Question Id must be provided")
            }
            issueReport.timeStamp.isBlank() -> {
                ValidationResult.Invalid(reason = "Timestamp must be provided")
            }
            !issueReport.userEmail.isNullOrBlank() && !issueReport.userEmail.trim().matches(emailRegex) -> {
                ValidationResult.Invalid(reason = "User email is invalid")
            }
            issueReport.additionalComment != null && issueReport.additionalComment.length < 5 -> {
                ValidationResult.Invalid(reason = "Additional comment must be at least more than 5 chars")
            }
            else -> {
                ValidationResult.Valid
            }

        }
    }
}