package com.example.hellomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hellomobile.navigation.Screen
import com.example.hellomobile.ui.TugasViewModel
import com.example.hellomobile.ui.screens.DaftarTugasScreen
import com.example.hellomobile.ui.screens.DetailTugasScreen
import com.example.hellomobile.ui.screens.EditTugasScreen
import com.example.hellomobile.ui.screens.TambahTugasScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TugasAppNavigation()
                }
            }
        }
    }
}

@Composable
fun TugasAppNavigation(viewModel: TugasViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Daftar.route
    ) {
        composable(Screen.Daftar.route) {
            DaftarTugasScreen(
                viewModel = viewModel,
                onTambahClick = {
                    navController.navigate(Screen.Tambah.route)
                },
                onTugasClick = { tugasId ->
                    navController.navigate(Screen.Detail.createRoute(tugasId))
                }
            )
        }

        composable(Screen.Tambah.route) {
            TambahTugasScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("tugasId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tugasId = backStackEntry.arguments?.getInt("tugasId") ?: 0
            DetailTugasScreen(
                tugasId = tugasId,
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onEditClick = { id ->
                    navController.navigate(Screen.Edit.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.Edit.route,
            arguments = listOf(navArgument("tugasId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tugasId = backStackEntry.arguments?.getInt("tugasId") ?: 0
            EditTugasScreen(
                tugasId = tugasId,
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}