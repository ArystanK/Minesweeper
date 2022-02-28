package kz.arctan.minesweaper.game.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.arctan.minesweaper.game.domain.models.GameMode

@Composable
@Preview
fun ChooseGameModeScreen() {
    var numberOfBombs by remember { mutableStateOf(10) }
    var width by remember { mutableStateOf(10) }
    var height by remember { mutableStateOf(10) }
    ChooseGameModeView(
        gameModes = listOf(
            GameMode.Easy,
            GameMode.Medium,
            GameMode.Hard
        ),
        numberOfBombs = numberOfBombs.toString(),
        onChangeNumberOfBombs = { numberOfBombs = it.toInt() },
        width = width.toString(),
        onChangeWidth = { width = it.toInt() },
        height = height.toString(),
        onChangeHeight = { height = it.toInt() }
    )
}

@Composable
fun ChooseGameModeView(
    gameModes: List<GameMode>,
    numberOfBombs: String,
    onChangeNumberOfBombs: (String) -> Unit,
    height: String,
    onChangeHeight: (String) -> Unit,
    width: String,
    onChangeWidth: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text("Game modes", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        gameModes.forEach {
            CustomGameModeTile(
                title = it.toString(),
                numberOfBombs = it.numberOfBombs.toString(),
                onChangeHeight = {},
                onChangeWidth = {},
                onChangeNumberOfBombs = {},
                width = it.width.toString(),
                height = it.height.toString(),
                enabled = false,
                onPlayButtonClick = {
                    // navigate to the Game screen with specified with, height and number of bombs
                }
            )
        }
        CustomGameModeTile(
            title = "Custom",
            numberOfBombs = numberOfBombs,
            onChangeNumberOfBombs = onChangeNumberOfBombs,
            height = height,
            onChangeHeight = onChangeHeight,
            width = width,
            onChangeWidth = onChangeWidth,
            enabled = true,
            onPlayButtonClick = {
                TODO("Navigate to the Game Screen")
            }
        )
    }
}

/***
 * Decided to use the second variant, nut mb change it in future, idk
 */

@Composable
fun GameModeTile(gameMode: GameMode) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(gameMode.toString())
        Text(text = "Number of bombs = ${gameMode.numberOfBombs}/width = ${gameMode.width}/height = ${gameMode.height}")
    }
}

@Composable
fun CustomGameModeTile(
    title: String,
    numberOfBombs: String,
    enabled: Boolean,
    onChangeNumberOfBombs: (String) -> Unit,
    height: String,
    onChangeHeight: (String) -> Unit,
    width: String,
    onChangeWidth: (String) -> Unit,
    onPlayButtonClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = numberOfBombs,
            onValueChange = onChangeNumberOfBombs,
            label = { Text("Bombs") },
            modifier = Modifier.width(90.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            enabled = enabled
        )
        TextField(
            value = width,
            onValueChange = onChangeWidth,
            label = { Text("Width") },
            modifier = Modifier.width(90.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            enabled = enabled
        )
        TextField(
            value = height,
            onValueChange = onChangeHeight,
            label = { Text("Height") },
            modifier = Modifier.width(90.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            enabled = enabled
        )
        IconButton(onClick = onPlayButtonClick) {
            Card(
                backgroundColor = Color.Black,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.Blue,
                    modifier = Modifier.fillMaxWidth(0.6f)
                )
            }
        }
    }
}
