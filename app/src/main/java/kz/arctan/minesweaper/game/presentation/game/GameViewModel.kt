package kz.arctan.minesweaper.game.presentation.game

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.arctan.minesweaper.game.domain.util.PlaygroundGenerationUseCase
import javax.inject.Inject

class GameViewModel(
    playgroundGenerationUseCase: PlaygroundGenerationUseCase,
    numberOfBombs: Int,
    width: Int,
    height: Int
) : ViewModel() {
    private var playground = playgroundGenerationUseCase.invoke(numberOfBombs, width, height)


}