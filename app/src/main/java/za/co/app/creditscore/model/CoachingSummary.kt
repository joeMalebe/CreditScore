package za.co.app.creditscore.model

data class CoachingSummary (
    val activeTodo: Boolean? = null,
    val activeChat: Boolean? = null,
    val numberOfTodoItems: Long? = null,
    val numberOfCompletedTodoItems: Long? = null,
    val selected: Boolean? = null
)