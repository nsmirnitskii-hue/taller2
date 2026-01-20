package com.example.taller2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Update
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CocheDao {

    @Insert
    suspend fun insert(coche: Coche)

    @Update
    suspend fun update(coche: Coche)

    @Delete
    suspend fun delete(coche: Coche)

    @Query("SELECT * FROM coche")
    fun getAll(): Flow<List<Coche>>

    @Query("SELECT * FROM coche WHERE id = :id")
    fun getById(id: Int): Flow<Coche>


    @Query("""
        SELECT * FROM coche
        WHERE (:matricula IS NULL OR matricula LIKE '%' || :matricula || '%')
        AND (:modelo IS NULL OR modelo LIKE '%' || :modelo || '%')
    """)
    fun filtrarCoches(matricula: String?, modelo: String): Flow<List<Coche>>


    @Query("DELETE FROM coche WHERE id = :id")
    suspend fun deleteById(id: Int)
}