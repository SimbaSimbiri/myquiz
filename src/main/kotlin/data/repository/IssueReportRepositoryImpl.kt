package com.simbiri.data.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.simbiri.data.database.entity.IssueReportEntity
import com.simbiri.data.mapper.toIssueReport
import com.simbiri.data.mapper.toIssueReportEntity
import com.simbiri.data.util.Constants.ISSUE_REPORT_COLLECTION
import com.simbiri.domain.model.IssueReport
import com.simbiri.domain.repository.IssueReportRepository
import com.simbiri.domain.util.DataError
import com.simbiri.domain.util.ResultType
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class IssueReportRepositoryImpl(mongoDatabase: MongoDatabase) : IssueReportRepository {
    private val issueCollection = mongoDatabase.getCollection<IssueReportEntity>(ISSUE_REPORT_COLLECTION)

    override suspend fun getAllIssueReports(): ResultType<List<IssueReport>, DataError> {
        return try {
            val reports = issueCollection
                .find()
                .map { it.toIssueReport() }
                .toList()

            if (reports.isNotEmpty()) {
                ResultType.Success(reports)
            } else {
                ResultType.Failure(DataError.NotFound)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }

    override suspend fun insertIssueReport(issueReport: IssueReport): ResultType<Unit, DataError> {
        return try {
            issueCollection.insertOne(issueReport.toIssueReportEntity())
            ResultType.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }

    override suspend fun deleteIssueReport(reportId: String?): ResultType<Unit, DataError> {
        if (reportId.isNullOrBlank()) {
            return ResultType.Failure(DataError.ValidationError)
        }

        return try {
            val filterQuery = Filters.eq(IssueReportEntity::_id.name, reportId)
            val deletedResult = issueCollection.deleteOne(filterQuery)
            if (deletedResult.deletedCount > 0L) {
                ResultType.Success(Unit)
            } else {
                ResultType.Failure(DataError.NotFound)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            ResultType.Failure(DataError.DatabaseError)
        }
    }
}