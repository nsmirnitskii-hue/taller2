package com.example.taller2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mecanico")
data class Mecanico(
@PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dni: String,
    val nombre: String
)