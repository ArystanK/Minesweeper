package kz.arctan.minesweaper.common.domain.model

sealed interface ActionResult {
    object Success : ActionResult
    data class Error(val message: String) : ActionResult
    object Loading : ActionResult
}