package kz.arctan.minesweaper.game.presentation.game

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kz.arctan.minesweaper.game.domain.models.Cell
import kz.arctan.minesweaper.game.domain.util.PlayerClickCheckUseCase
import kz.arctan.minesweaper.game.domain.util.PlaygroundGenerationUseCase

@Composable
@Preview
fun GameScreenPreview() {
    Playground(
        playgroundData = PlaygroundGenerationUseCase().invoke(10, 8, 10),
        width = 8,
        height = 10,
        bombs = 10
    ) {}
}

@Composable
fun GameScreen(navController: NavController, width: Int, height: Int, bombs: Int) {
//    val gameViewModel = hiltViewModel<GameViewModel>()

    val playground = rememberSaveable {
        mutableStateOf(
            PlaygroundGenerationUseCase()(bombs, width, height)
        )
    }
    Playground(
        playgroundData = playground.value,
        width = width,
        height = height,
        bombs = bombs
    ) {
        playground.value = PlaygroundGenerationUseCase()(bombs, width, height)
    }
}

@Composable
fun Playground(
    playgroundData: Array<Array<Cell>>,
    width: Int,
    height: Int,
    bombs: Int,
    playAgain: () -> Unit
) {
    var showLooseMessage by rememberSaveable { mutableStateOf(false) }
    var showWinMessage by rememberSaveable { mutableStateOf(false) }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(count = playgroundData.size) { rowId ->
            val scrollState = ScrollState(0)
            Row(modifier = Modifier.fillMaxSize()) {
                for (colId in playgroundData.first().indices)
                    Tile(
                        content = playgroundData[rowId][colId].content,
                        opened = playgroundData[rowId][colId].opened.value,
                        markedAsBomb = playgroundData[rowId][colId].markedAsBomb.value,
                        onClick = {
                            if (!PlayerClickCheckUseCase(playgroundData)(rowId, colId))
                                showLooseMessage = true
                            if (playgroundData.flatten()
                                    .count { it.opened.value } == width * height - bombs
                            ) {
                                showWinMessage = true
                            }
                        },
                        markAsBomb = {
                            playgroundData[rowId][colId].markedAsBomb.value = true
                        }
                    )

            }
        }
    }
    if (showLooseMessage) {
        AlertDialog(
            buttons = {
                Row {
                    TextButton(onClick = {
                        showLooseMessage = false
                    }) {
                        Text(text = "Close")
                    }
                    TextButton(onClick = {
                        playAgain()
                        showLooseMessage = false
                    }) {
                        Text(text = "Play again")
                    }
                }
            },
            onDismissRequest = {
                showLooseMessage = false
            },
            title = {
                Text("You have lost")
            }
        )
    }
    if (showWinMessage) {
        AlertDialog(
            buttons = {
                Row {
                    TextButton(onClick = {
                        showWinMessage = false
                    }) {
                        Text(text = "Close")
                    }
                    TextButton(onClick = {
                        playAgain()
                        showWinMessage = false
                    }) {
                        Text(text = "Play again")
                    }
                }
            },
            onDismissRequest = {
                showWinMessage = false
            },
            title = {
                Text("You have won! Congrats")
            }
        )
    }
}


@Composable
fun Tile(
    content: Char,
    opened: Boolean,
    onClick: () -> Unit,
    markAsBomb: () -> Unit,
    markedAsBomb: Boolean
) {
    TextButton(
        modifier = Modifier
            .size(50.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        markAsBomb()
                    },
                    onDoubleTap = {
                        markAsBomb()
                    }
                )
            },
        onClick = onClick,
//        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan)
    ) {
        Text(
            if (markedAsBomb) "?" else if (opened) if (content == '0') " " else content.toString() else "#",
            fontSize = 20.sp
        )
    }
}

//@Preview
//@Composable
//fun TilePreview() {
//    Tile('1')
//}