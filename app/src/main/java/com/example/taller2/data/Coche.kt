package com.example.taller2.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "coche",
    foreignKeys = [
        ForeignKey(
            entity = Mecanico::class,
            parentColumns = ["id"],
            childColumns = ["id_mecanico"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.NO_ACTION
        )
    ])
data class Coche(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val matricula: String,
    val modelo: String,
    val color: String,
    val fechaEntrada: String,
    val nombreResponsable: String,
    val id_mecanico: Int
)