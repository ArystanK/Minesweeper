package kz.arctan.minesweaper.game.domain.models

sealed class GameMode(var numberOfBombs: Int, var width: Int, var height: Int) {
    object Easy : GameMode(10, 8, 10) {
        override fun toString(): String {
            return "Easy"
        }
    }

    object Medium : GameMode(70, 8, 15) {
        override fun toString(): String {
            return "Medium"
        }
    }

    object Hard : GameMode(150, 8, 16) {
        override fun toString(): String {
            return "Hard"
        }
    }

    class Custom(numberOfBombs: Int = 10, width: Int = 8, height: Int = 10) :
        GameMode(numberOfBombs, width, height)
}