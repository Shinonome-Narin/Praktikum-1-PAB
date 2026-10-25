package com.example.hellomobile.navigation

sealed class Screen(val route: String) {
    object Daftar : Screen("daftar")
    object Tambah : Screen("tambah")
    object Detail : Screen("detail/{tugasId}") {
        fun createRoute(tugasId: Int) = "detail/$tugasId"
    }
    object Edit : Screen("edit/{tugasId}") {
        fun createRoute(tugasId: Int) = "edit/$tugasId"
    }
}