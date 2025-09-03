package com.simbiri.data.mapper

import com.simbiri.data.database.entity.IssueReportEntity
import com.simbiri.domain.model.IssueReport

fun IssueReportEntity.toIssueReport(): IssueReport = IssueReport(
    id = _id,
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timeStamp = timeStamp
)

fun IssueReport.toIssueReportEntity(): IssueReportEntity = IssueReportEntity(
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timeStamp = timeStamp
)