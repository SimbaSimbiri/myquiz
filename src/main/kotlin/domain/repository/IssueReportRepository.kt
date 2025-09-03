package com.simbiri.domain.repository

import com.simbiri.domain.model.IssueReport
import com.simbiri.domain.util.DataError
import com.simbiri.domain.util.ResultType

interface IssueReportRepository {
    suspend fun getAllIssueReports(): ResultType<List<IssueReport>, DataError>
    suspend fun insertIssueReport(issueReport: IssueReport): ResultType<Unit, DataError>
    suspend fun deleteIssueReport(reportId: String?): ResultType<Unit, DataError>
}