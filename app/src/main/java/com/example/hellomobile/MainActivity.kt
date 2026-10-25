package com.example.hellomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import data.AppDatabase
import repository.TugasRepository
import ui.TugasViewModel
import ui.TugasViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi Database dan Repository
        val database = AppDatabase.getDatabase(this)
        val repository = TugasRepository(database.tugasDao())

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AplikasiManajemenTugas(repository)
                }
            }
        }
    }
}

@Composable
fun AplikasiManajemenTugas(repository: TugasRepository) {
    val navController = rememberNavController()
    // Inisialisasi ViewModel
    val viewModel: TugasViewModel = viewModel(factory = TugasViewModelFactory(repository))

    // Tarik data tugas dari StateFlow
    val daftarTugas by viewModel.daftarTugas.collectAsState()

    NavHost(navController = navController, startDestination = "daftar") {
        composable("daftar") {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Total Tugas Tersimpan: ${daftarTugas.size}")
                Button(onClick = { navController.navigate("tambah") }) {
                    Text("Ke Form Tambah Tugas")
                }
            }
        }
        composable("tambah") {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Layar Form Tambah Tugas")
                Button(onClick = { navController.navigate("daftar") }) {
                    Text("Kembali ke Daftar")
                }
            }
        }
        composable("detail") {
            Text("Layar Detail Tugas")
        }
        composable("edit") {
            Text("Layar Edit Tugas")
        }
    }
}