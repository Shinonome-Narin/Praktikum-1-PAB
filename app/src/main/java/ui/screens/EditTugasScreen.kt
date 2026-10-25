package com.example.hellomobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hellomobile.ui.TugasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTugasScreen(
    tugasId: Int,
    viewModel: TugasViewModel,
    onNavigateBack: () -> Unit
) {
    val tugasState by viewModel.getTugasById(tugasId).collectAsState(initial = null)

    var judul by remember { mutableStateOf("") }
    var mataKuliah by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var prioritas by remember { mutableStateOf("Normal") }
    var status by remember { mutableStateOf("Belum Selesai") }

    LaunchedEffect(tugasState) {
        tugasState?.let { tugas ->
            judul = tugas.judul
            mataKuliah = tugas.mataKuliah
            deskripsi = tugas.deskripsi
            deadline = tugas.deadline
            prioritas = tugas.prioritas
            status = tugas.status
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Tugas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        val tugas = tugasState
        if (tugas == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = judul,
                    onValueChange = { judul = it },
                    label = { Text("Judul Tugas *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = mataKuliah,
                    onValueChange = { mataKuliah = it },
                    label = { Text("Mata Kuliah *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = deskripsi,
                    onValueChange = { deskripsi = it },
                    label = { Text("Deskripsi Tugas") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                OutlinedTextField(
                    value = deadline,
                    onValueChange = { deadline = it },
                    label = { Text("Deadline") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Text("Prioritas", style = MaterialTheme.typography.titleSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Rendah", "Normal", "Tinggi").forEach { item ->
                        FilterChip(
                            selected = prioritas == item,
                            onClick = { prioritas = item },
                            label = { Text(item) }
                        )
                    }
                }

                Text("Status Tugas", style = MaterialTheme.typography.titleSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Belum Selesai", "Proses", "Selesai").forEach { item ->
                        FilterChip(
                            selected = status == item,
                            onClick = { status = item },
                            label = { Text(item) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (judul.isNotBlank() && mataKuliah.isNotBlank()) {
                            viewModel.ubahTugas(
                                tugas.copy(
                                    judul = judul,
                                    mataKuliah = mataKuliah,
                                    deskripsi = deskripsi,
                                    deadline = deadline,
                                    prioritas = prioritas,
                                    status = status
                                )
                            )
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = judul.isNotBlank() && mataKuliah.isNotBlank()
                ) {
                    Text("Perbarui Tugas")
                }
            }
        }
    }
}