package com.example.taller2.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "reparacion",
    foreignKeys = [
        ForeignKey(
            entity = Coche::class,
            parentColumns = ["id"],
            childColumns = ["id_coche"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.NO_ACTION
        )
    ])
data class Reparacion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val descripcion: String,
    val coasre: Int,
    val id_coche: Int
)