package kz.arctan.minesweaper.game.domain.util

import kz.arctan.minesweaper.game.domain.models.Cell
import kotlin.random.Random

class PlaygroundGenerationUseCase {
    fun invoke(numberOfBombs: Int, width: Int, height: Int): Array<Array<Cell>> {
        val playground = Array(height) { Array(width) { Cell('0') } }
        repeat(numberOfBombs) {
            var columnId = Random.nextInt(width)
            var rowId = Random.nextInt(height)
            while (playground[rowId][columnId].content == '*') {
                columnId = Random.nextInt(width)
                rowId = Random.nextInt(height)
            }
            playground[rowId][columnId].content = '*'
            val directions =
                arrayOf(1 to 1, 1 to 0, 0 to 1, -1 to 1, -1 to -1, -1 to 0, 0 to -1, 1 to -1)
            for (direction in directions) {
                val newRowId = rowId + direction.first
                val newColId = columnId + direction.second
                if (newRowId !in 0 until height ||
                    newColId !in 0 until width ||
                    playground[newRowId][newColId].content == '*'
                ) continue
                playground[newRowId][newColId].content =
                    (playground[newRowId][newColId].content.digitToInt() + 1).digitToChar()
            }
        }
        return playground
    }
}