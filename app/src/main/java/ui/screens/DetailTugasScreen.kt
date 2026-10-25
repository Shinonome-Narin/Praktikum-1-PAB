package com.example.hellomobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hellomobile.ui.TugasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTugasScreen(
    tugasId: Int,
    viewModel: TugasViewModel,
    onNavigateBack: () -> Unit,
    onEditClick: (Int) -> Unit
) {
    val tugasState by viewModel.getTugasById(tugasId).collectAsState(initial = null)
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Tugas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    tugasState?.let { tugas ->
                        IconButton(onClick = { onEditClick(tugas.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error)
                        }
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = tugas.judul, style = MaterialTheme.typography.headlineSmall)
                        Text(text = "Mata Kuliah: ${tugas.mataKuliah}", style = MaterialTheme.typography.titleMedium)

                        HorizontalDivider()

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AssistChip(
                                onClick = { },
                                label = { Text("Status: ${tugas.status}") }
                            )
                            AssistChip(
                                onClick = { },
                                label = { Text("Prioritas: ${tugas.prioritas}") }
                            )
                        }

                        Text(text = "Deadline: ${tugas.deadline}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)

                        HorizontalDivider()

                        Text(text = "Deskripsi:", style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = if (tugas.deskripsi.isNotBlank()) tugas.deskripsi else "(Tidak ada deskripsi)",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { onEditClick(tugas.id) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Edit")
                    }
                    Button(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Hapus")
                    }
                }
            }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Konfirmasi Hapus") },
                    text = { Text("Apakah Anda yakin ingin menghapus tugas ini?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.hapus(tugas)
                                showDeleteDialog = false
                                onNavigateBack()
                            }
                        ) {
                            Text("Hapus", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Batal")
                        }
                    }
                )
            }
        }
    }
}