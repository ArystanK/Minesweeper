package kz.arctan.minesweaper.game.presentation.chooseGame

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import kz.arctan.minesweaper.common.domain.util.Routes
import kz.arctan.minesweaper.game.domain.models.GameMode

@Composable
@Preview
fun ChooseGameModeScreenPreview() {
    var numberOfBombs by remember { mutableStateOf(10) }
    var width by remember { mutableStateOf(8) }
    var height by remember { mutableStateOf(10) }
    ChooseGameModeView(
        gameModes = listOf(
            GameMode.Easy,
            GameMode.Medium,
            GameMode.Hard
        ),
        numberOfBombs = numberOfBombs,
        onChangeNumberOfBombs = { numberOfBombs = it },
        width = width,
        onChangeWidth = { width = it },
        height = height,
        onChangeHeight = { height = it },
        onPlayButtonClick = {}
    )
}

@Composable
fun ChooseGameModeScreen(navController: NavController) {
    var numberOfBombs by remember { mutableStateOf(10) }
    var width by remember { mutableStateOf(8) }
    var height by remember { mutableStateOf(10) }
    ChooseGameModeView(
        gameModes = listOf(
            GameMode.Easy,
            GameMode.Medium,
//            GameMode.Hard
        ),
        numberOfBombs = numberOfBombs,
        onChangeNumberOfBombs = { numberOfBombs = it },
        width = width,
        onChangeWidth = { width = it },
        height = height,
        onChangeHeight = { height = it },
        onPlayButtonClick = {
            navController.navigate("${Routes.GAME_SCREEN}/${it.width}/${it.height}/${it.numberOfBombs}")
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChooseGameModeView(
    gameModes: List<GameMode>,
    numberOfBombs: Int,
    onChangeNumberOfBombs: (Int) -> Unit,
    height: Int,
    onChangeHeight: (Int) -> Unit,
    width: Int,
    onChangeWidth: (Int) -> Unit,
    onPlayButtonClick: (type: GameMode) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        stickyHeader {
            Text("Game modes", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }
        items(gameModes) {
            CustomGameModeTile(
                numberOfBombs = it.numberOfBombs,
                onChangeNumberOfBombs = {},
                height = it.height,
                onChangeHeight = {},
                width = it.width,
                onChangeWidth = {},
                enabled = false
            ) { onPlayButtonClick(it) }
        }
        item {
            CustomGameModeTile(
                numberOfBombs = numberOfBombs,
                onChangeNumberOfBombs = onChangeNumberOfBombs,
                height = height,
                onChangeHeight = onChangeHeight,
                width = width,
                onChangeWidth = onChangeWidth,
                enabled = true
            ) {
                onPlayButtonClick(
                    GameMode.Custom(
                        numberOfBombs = numberOfBombs,
                        width = width,
                        height = height
                    )
                )
            }
        }
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
    numberOfBombs: Int,
    onChangeNumberOfBombs: (Int) -> Unit,
    height: Int,
    onChangeHeight: (Int) -> Unit,
    width: Int,
    onChangeWidth: (Int) -> Unit,
    enabled: Boolean,
    onPlayButtonClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (enabled) NumberPicker(
            value = numberOfBombs,
            onValueChange = onChangeNumberOfBombs,
            modifier = Modifier.width(90.dp),
            range = 1..20
        ) else Text(
            numberOfBombs.toString(),
            modifier = Modifier.width(90.dp)
        )
        if (enabled) NumberPicker(
            value = width,
            onValueChange = onChangeWidth,
            modifier = Modifier.width(90.dp),
            range = 1..8
        ) else Text(
            text = width.toString(),
            modifier = Modifier.width(90.dp)
        )
        if (enabled) NumberPicker(
            value = height,
            onValueChange = onChangeHeight,
            modifier = Modifier.width(90.dp),
            range = 1..20
        ) else Text(
            height.toString(),
            modifier = Modifier.width(90.dp)
        )
        IconButton(onClick = onPlayButtonClick) {
            Card(
                backgroundColor = Color.Black,
//                modifier = Modifier.fillMaxSize()
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
