package com.example.taller2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MecanicoDao {
    @Query("SELECT * from mecanico ORDER BY nombre ASC")
    fun getAllmecanico(): Flow<List<Mecanico>>

    @Query("SELECT * from mecanico WHERE id = :id")
    fun getmecanico(id: Int): Flow<Mecanico>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: Mecanico)

    @Update()
    suspend fun update(entity: Mecanico)

    @Delete
    suspend fun delete(entity: Mecanico)
}