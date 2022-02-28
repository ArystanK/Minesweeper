package kz.arctan.minesweaper.game.presentation

import androidx.lifecycle.ViewModel
import kz.arctan.minesweaper.game.domain.util.PlaygroundGenerationUseCase

class GameViewModel(
    playgroundGenerationUseCase: PlaygroundGenerationUseCase,
    numberOfBombs: Int,
    width: Int,
    height: Int
) : ViewModel() {
    private var playground = playgroundGenerationUseCase.invoke(numberOfBombs, width, height)


}