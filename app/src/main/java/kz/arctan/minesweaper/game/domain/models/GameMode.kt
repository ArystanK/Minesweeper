package kz.arctan.minesweaper.game.domain.models

sealed class GameMode(var numberOfBombs: Int, var width: Int, var height: Int) {
    object Easy : GameMode(10, 10, 10)
    object Medium : GameMode(70, 15, 15)
    object Hard : GameMode(150, 16, 16)
    class Custom(numberOfBombs: Int = 10, width: Int = 10, height: Int = 10) :
        GameMode(numberOfBombs, width, height)
}