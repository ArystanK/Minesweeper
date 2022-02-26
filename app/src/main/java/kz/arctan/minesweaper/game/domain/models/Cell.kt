package kz.arctan.minesweaper.game.domain.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Cell(
    var content: Char,
    val opened: MutableState<Boolean> = mutableStateOf(false),
    val markedAsBomb: MutableState<Boolean> = mutableStateOf(false)
)
