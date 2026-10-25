package data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TugasDao {
    @Query("SELECT * FROM tugas ORDER BY id DESC")
    fun getAllTugas(): Flow<List<TugasEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTugas(tugas: TugasEntity)

    @Update
    suspend fun updateTugas(tugas: TugasEntity)

    @Delete
    suspend fun deleteTugas(tugas: TugasEntity)
}