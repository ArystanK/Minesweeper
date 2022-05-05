package kz.arctan.minesweaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import kz.arctan.minesweaper.auth.presentation.login.ui.LoginScreen
import kz.arctan.minesweaper.auth.presentation.register.ui.RegistrationScreen
import kz.arctan.minesweaper.common.domain.util.Routes
import kz.arctan.minesweaper.common.pesentation.theme.MinesweaperTheme
import kz.arctan.minesweaper.game.presentation.chooseGame.ChooseGameModeScreen
import kz.arctan.minesweaper.game.presentation.game.GameScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MinesweaperTheme {
                NavHost(navController = navController, startDestination = Routes.CHOOSE_GAME_MODE_SCREEN) {
                    composable(Routes.LOGIN_SCREEN) {
                        LoginScreen(
                            navController = navController,
                            viewModel = hiltViewModel(),
                            onNavigate = { navController.navigate(it.route) })
                    }
                    composable(Routes.REGISTER_SCREEN) {
                        RegistrationScreen(
                            registrationViewModel = hiltViewModel(),
                            navController = navController
                        )
                    }
                    composable(Routes.CHOOSE_GAME_MODE_SCREEN) {
                        ChooseGameModeScreen(navController)
                    }
                    composable(
                        "${Routes.GAME_SCREEN}/{width}/{height}/{bombs}",
                        arguments = listOf(
                            navArgument("width") { type = NavType.IntType },
                            navArgument("height") { type = NavType.IntType },
                            navArgument("bombs") { type = NavType.IntType }
                        )
                    ) {
                        GameScreen(
                            navController = navController,
                            width = it.arguments?.getInt("width") ?: 8,
                            height = it.arguments?.getInt("height") ?: 10,
                            bombs = it.arguments?.getInt("bombs") ?: 10
                        )
                    }
                }
            }
        }
    }
}
