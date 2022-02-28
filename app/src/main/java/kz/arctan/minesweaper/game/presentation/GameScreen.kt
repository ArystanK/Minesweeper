package kz.arctan.minesweaper.game.presentation

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.arctan.minesweaper.game.domain.models.Cell
import kz.arctan.minesweaper.game.domain.util.PlayerClickCheckUseCase
import kz.arctan.minesweaper.game.domain.util.PlaygroundGenerationUseCase

@Composable
@Preview
fun GameScreen() {
    Playground(playgroundData = PlaygroundGenerationUseCase().invoke(10, 10, 10))
}

@Composable
fun Playground(playgroundData: Array<Array<Cell>>) {
    var showLooseMessage by remember { mutableStateOf(false) }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(count = playgroundData.size) { rowId ->
            LazyRow {
                items(count = playgroundData[0].size) { colId ->
                    Tile(
                        content = playgroundData[rowId][colId].content,
                        opened = playgroundData[rowId][colId].opened.value,
                        markedAsBomb = playgroundData[rowId][colId].markedAsBomb.value,
                        onClick = {
                            if (!PlayerClickCheckUseCase(playgroundData)(rowId, colId))
                                showLooseMessage = true
                        },
                        markAsBomb = {
                            playgroundData[rowId][colId].markedAsBomb.value = true
                        }
                    )
                }
            }
        }
    }
    if (showLooseMessage) {
        Snackbar {
            Text("You have lost")
        }
    }
}


@Composable
fun Tile(
    content: Char,
    opened: Boolean,
    onClick: () -> Unit,
    markAsBomb: (_: Offset) -> Unit,
    markedAsBomb: Boolean
) {
    TextButton(
        modifier = Modifier
            .size(50.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = markAsBomb)
            },
        onClick = onClick,
//        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan)
    ) {
        Text(if (markedAsBomb) "?" else if (opened) content.toString() else "#", fontSize = 20.sp)
    }
}

//@Preview
//@Composable
//fun TilePreview() {
//    Tile('1')
//}