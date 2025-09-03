package com.simbiri.presentation.routes.issue_reports

import com.simbiri.domain.repository.IssueReportRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.delete
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

fun Route.deleteIssueReportById(issueReportRepository: IssueReportRepository) {
    delete<IssueReportsRoutesPath.ById> {path ->
        issueReportRepository.deleteIssueReport(path.reportId)
            .onSuccess {
                call.respond(HttpStatusCode.NoContent)
            }
            .onFailure { error ->
                respondWithError(error)
            }

    }
}