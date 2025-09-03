package com.simbiri.presentation.routes.issue_reports

import com.simbiri.domain.repository.IssueReportRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllIssueReports(issueReportRepository: IssueReportRepository) {
    get <IssueReportsRoutesPath>{
        issueReportRepository.getAllIssueReports()
            .onSuccess { issues ->
                call.respond(message = issues, status = HttpStatusCode.OK)
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }

}