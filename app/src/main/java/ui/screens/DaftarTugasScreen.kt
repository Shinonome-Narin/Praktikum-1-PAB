package com.example.hellomobile.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hellomobile.data.TugasEntity
import com.example.hellomobile.ui.TugasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarTugasScreen(
    viewModel: TugasViewModel,
    onTambahClick: () -> Unit,
    onTugasClick: (Int) -> Unit
) {
    val listTugas by viewModel.semuaTugas.collectAsState(initial = emptyList())
    var tugasYangAkanDihapus by remember { mutableStateOf<TugasEntity?>(null) }
    var filterStatus by remember { mutableStateOf("Semua") }

    val filteredList = remember(listTugas, filterStatus) {
        if (filterStatus == "Semua") listTugas
        else listTugas.filter { it.status.equals(filterStatus, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manajemen Tugas Mahasiswa") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onTambahClick) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Tugas")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Filter Chip Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Semua", "Belum Selesai", "Proses", "Selesai").forEach { status ->
                    FilterChip(
                        selected = filterStatus == status,
                        onClick = { filterStatus = status },
                        label = { Text(status) }
                    )
                }
            }

            if (filteredList.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Belum Ada Tugas",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tekan tombol + untuk menambah tugas kuliah baru.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredList, key = { it.id }) { tugas ->
                        TugasItemCard(
                            tugas = tugas,
                            onClick = { onTugasClick(tugas.id) },
                            onDelete = { tugasYangAkanDihapus = tugas }
                        )
                    }
                }
            }
        }

        // Dialog Konfirmasi Hapus
        tugasYangAkanDihapus?.let { tugas ->
            AlertDialog(
                onDismissRequest = { tugasYangAkanDihapus = null },
                title = { Text("Konfirmasi Hapus") },
                text = { Text("Apakah Anda yakin ingin menghapus tugas '${tugas.judul}'?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.hapus(tugas)
                            tugasYangAkanDihapus = null
                        }
                    ) {
                        Text("Hapus", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { tugasYangAkanDihapus = null }) {
                        Text("Batal")
                    }
                }
            )
        }
    }
}

@Composable
fun TugasItemCard(
    tugas: TugasEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = tugas.judul, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "MK: ${tugas.mataKuliah}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(
                        onClick = { },
                        label = { Text(tugas.status, style = MaterialTheme.typography.labelSmall) }
                    )
                    AssistChip(
                        onClick = { },
                        label = { Text("Deadline: ${tugas.deadline}", style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Hapus Tugas",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}