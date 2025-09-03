package com.simbiri.presentation.routes.issue_reports

import com.simbiri.domain.model.IssueReport
import com.simbiri.domain.repository.IssueReportRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.routing.Route
import io.ktor.server.response.*

fun Route.insertIssueReport(issueReportRepository: IssueReportRepository) {
    post<IssueReportsRoutesPath> {
        val report = call.receive<IssueReport>()
        issueReportRepository.insertIssueReport(report)
            .onSuccess {
                call.respond(
                    message= "Issue reported successfully",
                    status= HttpStatusCode.Created
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}