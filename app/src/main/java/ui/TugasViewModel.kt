package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import data.TugasEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import repository.TugasRepository

class TugasViewModel(private val repository: TugasRepository) : ViewModel() {
    val daftarTugas: StateFlow<List<TugasEntity>> = repository.semuaTugas
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun simpanTugas(tugas: TugasEntity) = viewModelScope.launch {
        repository.tambahTugas(tugas)
    }

    fun ubahTugas(tugas: TugasEntity) = viewModelScope.launch {
        repository.perbaruiTugas(tugas)
    }

    fun hapusTugas(tugas: TugasEntity) = viewModelScope.launch {
        repository.hapusTugas(tugas)
    }
}

class TugasViewModelFactory(private val repository: TugasRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TugasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TugasViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}