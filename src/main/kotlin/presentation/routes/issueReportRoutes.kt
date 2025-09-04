package presentation.routes

import com.simbiri.domain.model.IssueReport
import com.simbiri.domain.repository.IssueReportRepository
import com.simbiri.domain.util.onFailure
import com.simbiri.domain.util.onSuccess
import com.simbiri.presentation.utils.respondWithError
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import presentation.routes.path.IssueReportsRoutesPath

fun Route.issueReportRoutes(issueReportRepository: IssueReportRepository) {

    // deleteIssueReportById
    delete<IssueReportsRoutesPath.ById> { path ->
        issueReportRepository.deleteIssueReport(path.reportId)
            .onSuccess {
                call.respond(HttpStatusCode.NoContent)
            }
            .onFailure { error ->
                respondWithError(error)
            }

    }

    // getAllIssueReports
    get<IssueReportsRoutesPath> {
        issueReportRepository.getAllIssueReports()
            .onSuccess { issues ->
                call.respond(message = issues, status = HttpStatusCode.OK)
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }

    // insertIssueReport
    post<IssueReportsRoutesPath> {
        val report = call.receive<IssueReport>()
        issueReportRepository.insertIssueReport(report)
            .onSuccess {
                call.respond(
                    message = "Issue reported successfully",
                    status = HttpStatusCode.Created
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }

}