package presentation.routes.path

import io.ktor.resources.Resource

@Resource("/report/issues")
class IssueReportsRoutesPath {

    @Resource("{reportId}")
    data class ById(
        val parent: IssueReportsRoutesPath = IssueReportsRoutesPath(),
        val reportId: String,
    )
}