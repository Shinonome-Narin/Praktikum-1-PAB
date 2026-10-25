package com.example.hellomobile.repository

import com.example.hellomobile.data.TugasDao
import com.example.hellomobile.data.TugasEntity
import kotlinx.coroutines.flow.Flow

class TugasRepository(private val tugasDao: TugasDao) {
    val semuaTugas: Flow<List<TugasEntity>> = tugasDao.getAllTugas()

    suspend fun tambahTugas(tugas: TugasEntity) {
        tugasDao.insertTugas(tugas)
    }

    suspend fun perbaruiTugas(tugas: TugasEntity) {
        tugasDao.updateTugas(tugas)
    }

    suspend fun hapusTugas(tugas: TugasEntity) {
        tugasDao.deleteTugas(tugas)
    }
}