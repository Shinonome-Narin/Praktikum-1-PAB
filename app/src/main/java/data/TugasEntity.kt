package com.example.hellomobile.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tugas")
data class TugasEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mataKuliah: String,
    val judul: String,
    val deskripsi: String = "",
    val deadline: String,
    val prioritas: String = "Normal",
    val status: String = "Belum Selesai"
)