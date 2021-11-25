package za.co.app.creditscore.model

data class CreditScoreModel(
    val accountIDVStatus: String? = null,
    val creditReportInfo: CreditReportInfo? = null,
    val dashboardStatus: String? = null,
    val personaType: String? = null,
    val coachingSummary: CoachingSummary? = null,
    val augmentedCreditScore: Any? = null
)

