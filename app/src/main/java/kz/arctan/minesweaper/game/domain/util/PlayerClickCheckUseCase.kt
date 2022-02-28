package kz.arctan.minesweaper.game.domain.util

import kz.arctan.minesweaper.game.domain.models.Cell

class PlayerClickCheckUseCase(private val playGround: Array<Array<Cell>>) {
    operator fun invoke(rowId: Int, colId: Int): Boolean {
        if (playGround[rowId][colId].content == '*') {
            playGround.forEach { row ->
                row.forEach { element ->
                    element.opened.value = true
                }
            }
            return false
        }
        if (playGround[rowId][colId].content == '0')
            check(rowId, colId)
        else
            playGround[rowId][colId].opened.value = true
        return true
    }

    private fun check(rowId: Int, colId: Int) {
        if (rowId !in playGround.indices ||
            colId !in playGround[0].indices ||
            playGround[rowId][colId].opened.value ||
            playGround[rowId][colId].content == '*'
        ) return
        playGround[rowId][colId].opened.value = true
        if (playGround[rowId][colId].content != '0') return

        val directions =
            arrayOf(1 to 1, 1 to 0, 0 to 1, -1 to 1, -1 to -1, -1 to 0, 0 to -1, 1 to -1)
        for (direction in directions) {
            check(rowId + direction.first, colId + direction.second)
        }
    }
}