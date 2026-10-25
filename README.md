# Aplikasi Manajemen Tugas Mahasiswa

Aplikasi mobile Android offline untuk mencatat dan mengelola tugas-tugas kuliah mahasiswa. Aplikasi ini dibuat menggunakan Kotlin, Jetpack Compose, ViewModel, Navigation Compose, dan Room Database.

## Fitur Utama

1. **Daftar Tugas**
   - Menampilkan seluruh daftar tugas kuliah yang tersimpan di database lokal.
   - Menyediakan filter status tugas (Semua, Belum Selesai, Proses, Selesai).
   - Dialog konfirmasi saat menghapus tugas.

2. **Tambah Tugas**
   - Form untuk menginput tugas baru (Judul, Mata Kuliah, Deskripsi, Deadline, Prioritas, Status).

3. **Detail Tugas**
   - Menampilkan rincian detail data tugas.

4. **Edit Tugas**
   - Form untuk memperbarui data tugas yang sudah tersimpan.

## Teknologi yang Digunakan

- Bahasa: Kotlin
- UI: Jetpack Compose (Material 3)
- State & Arsitektur: ViewModel, StateFlow
- Navigasi: Navigation Compose
- Database Lokal: Room Database

## Cara Menjalankan Aplikasi

1. Clone repositori ini ke komputer lokal.
2. Buka proyek menggunakan Android Studio.
3. Hubungkan emulator atau perangkat Android (Android 7.0 / API 24 ke atas).
4. Jalankan aplikasi melalui Android Studio (Run app).

## Refleksi Teknis

- **Keputusan Desain**:
  Aplikasi dirancang menggunakan arsitektur MVVM dan Room Database agar data tersimpan secara lokal dan tampilan UI diperbarui secara reaktif tanpa perlu koneksi internet.
- **Kendala**:
  Penyesuaian alur navigasi antar layar dan pengelolaan state pada form input.
- **Rencana Pengembangan**:
  Menambahkan fitur pencarian tugas dan notifikasi pengingat deadline.
