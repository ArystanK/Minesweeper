package kz.arctan.minesweaper.game.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kz.arctan.minesweaper.game.domain.models.Cell
import kz.arctan.minesweaper.game.domain.models.GameMode

@ExperimentalPagerApi
@Composable
@Preview
fun ChooseGameModeScreen() {
    ChooseGameModeView(
        gameModes = listOf(
            GameMode.Easy,
            GameMode.Medium,
            GameMode.Hard,
            GameMode.Custom()
        )
    )
}

@ExperimentalPagerApi
@Composable
fun ChooseGameModeView(gameModes: List<GameMode>) {
    val pagerState = rememberPagerState()

    HorizontalPager(count = 4) { page ->
        Box {
            Playground(
                playgroundData = Array(gameModes[page].height) {
                    Array(gameModes[page].width) {
                        Cell('0')
                    }
                }
            )
        }
    }
}