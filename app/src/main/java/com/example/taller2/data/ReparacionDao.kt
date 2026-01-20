package com.example.taller2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReparacionDao {
    @Query("SELECT * from reparacion ORDER BY descripcion ASC")
    fun getAllreparacion(): Flow<List<Reparacion>>

    @Query("SELECT * from reparacion WHERE id = :id")
    fun getreparacion(id: Int): Flow<Reparacion>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: Reparacion)

    @Update()
    suspend fun update(entity: Reparacion)

    @Delete
    suspend fun delete(entity: Reparacion)
}