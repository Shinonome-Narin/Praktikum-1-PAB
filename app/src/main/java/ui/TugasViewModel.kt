package com.example.hellomobile.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hellomobile.data.AppDatabase
import com.example.hellomobile.data.TugasEntity
import com.example.hellomobile.repository.TugasRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TugasViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository: TugasRepository = TugasRepository(
        AppDatabase.getDatabase(application).tugasDao()
    )

    val semuaTugas: StateFlow<List<TugasEntity>> = repository.semuaTugas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val daftarTugas: StateFlow<List<TugasEntity>> get() = semuaTugas

    fun tambah(tugas: TugasEntity) = viewModelScope.launch {
        repository.tambahTugas(tugas)
    }

    fun simpanTugas(tugas: TugasEntity) = tambah(tugas)

    fun ubahTugas(tugas: TugasEntity) = viewModelScope.launch {
        repository.perbaruiTugas(tugas)
    }

    fun hapus(tugas: TugasEntity) = viewModelScope.launch {
        repository.hapusTugas(tugas)
    }

    fun hapusTugas(tugas: TugasEntity) = hapus(tugas)
}

class TugasViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TugasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TugasViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}