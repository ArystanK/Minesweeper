package kz.arctan.minesweaper.game.presentation.game

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
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
        bombs = 10,
        playAgain = {},
        endGame = {}
    )
}

@Composable
fun GameScreen(navController: NavController, width: Int, height: Int, bombs: Int) {
//    val gameViewModel = hiltViewModel<GameViewModel>()

    val playground = rememberSaveable {
        mutableStateOf(
            PlaygroundGenerationUseCase()(bombs, width, height)
        )
    }
    var gameEnded by rememberSaveable { mutableStateOf(false) }
    val playAgain: () -> Unit = {
        playground.value = PlaygroundGenerationUseCase()(bombs, width, height)
        gameEnded = false
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Playground(
            playgroundData = playground.value,
            width = width,
            height = height,
            bombs = bombs,
            endGame = { gameEnded = true },
            playAgain = playAgain
        )
        if (gameEnded) {
            Button(onClick = playAgain) {
                Text(text = "Play again")
            }
        }
    }
}

@Composable
fun Playground(
    playgroundData: Array<Array<Cell>>,
    width: Int,
    height: Int,
    bombs: Int,
    endGame: () -> Unit,
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
                        open = {
                            if (!PlayerClickCheckUseCase(playgroundData)(rowId, colId)) {
                                showLooseMessage = true
                                endGame()
                            }
                            if (playgroundData.flatten()
                                    .count { it.opened.value } == width * height - bombs
                            ) {
                                showWinMessage = true
                                endGame()
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tile(
    content: Char,
    opened: Boolean,
    open: () -> Unit,
    markAsBomb: () -> Unit,
    markedAsBomb: Boolean
) {
    MyTextButton(
        modifier = Modifier
            .size(50.dp),
        onClick = {
            if (!markedAsBomb) open()
        },
        onLongClick = markAsBomb
    ) {
        Text(
            if (markedAsBomb) "?" else if (opened) if (content == '0') " " else content.toString() else "#",
            fontSize = 20.sp,
        )
    }
}

@Composable
fun MyTextButton(
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    content: @Composable RowScope.() -> Unit
) = ButtonWithLongPress(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    content = content,
    onLongClick = onLongClick
)

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ButtonWithLongPress(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onDoubleClick: () -> Unit = {},
    onLongClick: () -> Unit,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val contentColor by colors.contentColor(enabled)
    Surface(
        onClick = { },
        modifier = modifier
            .combinedClickable(
                interactionSource,
                rememberRipple(),
                true,
                null,
                Role.Button,
                null,
                onClick = { onClick() },
                onLongClick = { onLongClick() },
                onDoubleClick = { onDoubleClick() }),
        enabled = enabled,
        shape = shape,
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = border,
        elevation = elevation?.elevation(enabled, interactionSource)?.value ?: 0.dp,
        interactionSource = interactionSource,
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinWidth,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(contentPadding)
                        .combinedClickable(interactionSource,
                            null,
                            true,
                            null,
                            Role.Button,
                            null,
                            onClick = { onClick() },
                            onLongClick = { onLongClick() },
                            onDoubleClick = { onDoubleClick() }),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}